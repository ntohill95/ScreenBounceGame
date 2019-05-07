package com.example.firstandroidgame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class PipeSprite(var pipeNormal: Bitmap, var pipeUpsideDown: Bitmap, var x: Int, var y: Int) {


    fun draw(canvas: Canvas) {
        canvas.drawBitmap(pipeNormal, x.toFloat(), (-(gapHeight / 2) + y).toFloat(), null)
        canvas.drawBitmap(pipeUpsideDown, x.toFloat(), (Resources.getSystem().displayMetrics.heightPixels / 2).toFloat() + ((gapHeight / 2) + y).toFloat(), null)
    }

    fun update() {
        x -= velocityTracker
    }
}