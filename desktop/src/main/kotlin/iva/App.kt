package iva

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import core.MyGame
import core.World

fun main() {
    runApplication()
}

fun runApplication() {
    val screenX = 1200
    val screenY = 800
    val configuration = LwjglApplicationConfiguration().apply {
        title = "Gravity"
        width = screenX
        height = screenY
        for (size in intArrayOf(128,32,16)) {
            addIcon("moon$size.png", Files.FileType.Internal)
        }
    }
    World.x = screenX
    World.y = screenY
    LwjglApplication(MyGame(), configuration)
}

