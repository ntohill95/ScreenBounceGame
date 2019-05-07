package com.example.firstandroidgame

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var thread: MainThread
    private var characterSprite: CharacterSprite

    init {
        holder.addCallback(this)
        thread = MainThread(holder, this)
        isFocusable = true
        characterSprite = CharacterSprite(BitmapFactory.decodeResource(resources, R.drawable.llama))
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
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
    }

    fun update() {
        characterSprite.update()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        characterSprite.draw(canvas)


    }
}