package com.example.firstandroidgame

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread(private var surfaceHolder: SurfaceHolder, private var gameView: GameView) : Thread() {

    var running: Boolean = false
    private var canvas = Canvas()

    override fun run() {
        super.run()
        while (running) {
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(canvas)
                }
            } catch (e: Exception) {
                println(e.message)
            } finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }

    }
}