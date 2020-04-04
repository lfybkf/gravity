package iva

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import core.Game
import lib.LimitedQueue
import java.util.*

fun main() {

    runApplication()
}

fun runApplication() {
    val configuration = LwjglApplicationConfiguration().apply {
        title = "Gravity"
        width = 800
        height = 600
        for (size in intArrayOf(128,32,16)) {
            addIcon("moon$size.png", Files.FileType.Internal)
        }
    }
    LwjglApplication(Game(), configuration)
}

