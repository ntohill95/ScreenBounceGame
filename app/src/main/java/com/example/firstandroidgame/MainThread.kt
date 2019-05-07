package com.example.firstandroidgame

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread(surfaceHolder: SurfaceHolder, gameView: GameView) : Thread() {

    var running: Boolean = false
    private var canvas = Canvas()
    private var surface: SurfaceHolder = surfaceHolder
    private var gameView: GameView = gameView

    override fun run() {
        super.run()
        while (running) {
            try {
                canvas = surface.lockCanvas()
                synchronized(surface) {
                    gameView.update()
                    gameView.draw(canvas)
                }
            } catch (e: Exception) {
                println(e.message)
            } finally {
                try {
                    surface.unlockCanvasAndPost(canvas)
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }

    }
}