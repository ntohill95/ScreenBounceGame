package com.example.firstandroidgame

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.*
import kotlin.collections.ArrayList


var gapHeight = 500
var velocityTracker = 10

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var thread: MainThread
    private var characterSprite: CharacterSprite
    lateinit var pipe1: PipeSprite
    lateinit var pipe2: PipeSprite
    lateinit var pipe3: PipeSprite


    init {
        holder.addCallback(this)
        thread = MainThread(holder, this)
        isFocusable = true
        characterSprite = CharacterSprite(BitmapFactory.decodeResource(resources, R.drawable.llama))
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        characterSprite.y = characterSprite.y - (characterSprite.yVelocity * 10)
        return super.onTouchEvent(event)

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        var retry = true
        while (retry) {
            try {
                thread.running = false
                thread.join()
            } catch (e: InterruptedException) {
                println(e.message)
            }
            retry = false
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        thread.running = true
        thread.start()

        val bmp: Bitmap = getResizedBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.pillar_upside_down),
            500,
            (Resources.getSystem().displayMetrics.heightPixels / 2)
        )
        val bmp2: Bitmap = getResizedBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.pillar),
            500,
            (Resources.getSystem().displayMetrics.heightPixels / 2)
        )
        val y: Int
        val x: Int

        pipe1 = PipeSprite(bmp, bmp2, 0, 2000)
        pipe2 = PipeSprite(bmp, bmp2, -250, 3200)
        pipe3 = PipeSprite(bmp, bmp2, 250, 4500)
    }

    fun update() {
        characterSprite.update()
        pipe1.update()
        pipe2.update()
        pipe3.update()
        detectCrash()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawRGB(0, 100, 205)
        pipe1.draw(canvas)
        pipe2.draw(canvas)
        pipe3.draw(canvas)
        characterSprite.draw(canvas)
    }

    private fun detectCrash() {
        val pipes = ArrayList<PipeSprite>()
        pipes.add(pipe1)
        pipes.add(pipe2)
        pipes.add(pipe3)

        for (pipe in pipes) {
            //Detect if the character is touching one of the pipes
            if (characterSprite.y < pipe.y + (Resources.getSystem().displayMetrics.heightPixels / 2) - (gapHeight / 2) && characterSprite.x + 300 > pipe.x && characterSprite.x < pipe.x + 500) {
                resetLevel()
            } else if (characterSprite.y + 240 > (Resources.getSystem().displayMetrics.heightPixels / 2) + (gapHeight / 2) + pipe.y && characterSprite.x + 300 > pipe.x && characterSprite.x < pipe.x + 500) {
                resetLevel()
            }

            //Detect if the pipe has gone off the left of the screen and regenerate further ahead
            if (pipe.x + 500 < 0) {
                val r = Random()
                val value1 = r.nextInt(500)
                val value2 = r.nextInt(500)
                pipe.x = (Resources.getSystem().displayMetrics.heightPixels) + value1 + 1000
                pipe.y = value2 - 250
            }
        }

        //Detect if the character has gone off the bottom or top of the screen
        if (characterSprite.y + 240 < 0) {
            resetLevel()
        }
        if (characterSprite.y > (Resources.getSystem().displayMetrics.heightPixels)) {
            resetLevel()
        }
    }

    private fun resetLevel() {
        characterSprite.y = 100f
        pipe1.x = 2000
        pipe1.y = 0
        pipe2.x = 4500
        pipe2.y = 200
        pipe3.x = 3200
        pipe3.y = 250

    }

    private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        bm.recycle()
        return resizedBitmap
    }
}