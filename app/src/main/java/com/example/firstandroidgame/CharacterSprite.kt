package com.example.firstandroidgame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class CharacterSprite(bitmap: Bitmap) {
    var image: Bitmap = bitmap
    var x = (Resources.getSystem().displayMetrics.widthPixels/2).toFloat()
    var y = 100f
    var yVelocity = 5
    var xVelocity = 10


    init {
        image = Bitmap.createScaledBitmap(image, 200, 200, true)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x, y, null)
    }

    fun update() {

        y += yVelocity
    }
}