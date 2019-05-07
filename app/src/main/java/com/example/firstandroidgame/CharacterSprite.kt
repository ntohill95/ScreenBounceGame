package com.example.firstandroidgame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class CharacterSprite(bitmap: Bitmap) {
    var image: Bitmap = bitmap
    var x = 100f
    var y = 100f
    var xVelocity = 10
    var yVelocity = 5

    init {
        image = Bitmap.createScaledBitmap(image, 100, 100, true)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x, y, null)
    }

    fun update() {
        if (x < 0 && y < 0) {
            x = ((Resources.getSystem().displayMetrics.widthPixels) / 2).toFloat()
            y = ((Resources.getSystem().displayMetrics.widthPixels) / 2).toFloat()
        } else {
            x += xVelocity
            y += yVelocity

            if (x > (Resources.getSystem().displayMetrics.widthPixels - 100) || x < 0) {
                xVelocity *= -1
            }
            if (y > (Resources.getSystem().displayMetrics.heightPixels - 100) || y < 0) {
                yVelocity *= -1
            }
        }
    }
}