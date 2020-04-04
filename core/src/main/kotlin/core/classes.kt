package core

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.graphics.use
import lib.LimitedQueue

class Game : KtxGame<MainScreen>(MainScreen()) {
   override fun create() {
      super.create()
      World.g = 70000.0
   }
}

class MainScreen() : KtxScreen {
   private val length = 1000
   private val comets = listOf(
        Comet(Body(1.0, 200.0, 200.0, 12.0, 0.0), Color.RED, length)
        , Comet(Body(2.0, 300.0, 400.0, 5.0, -5.0), Color.GREEN, length)
        , Comet(Body(1.0, 400.0, 300.0, 5.0, -5.0), Color.BLUE, length)
   ).apply {
      forEach {comet ->
         World += comet.body
      }
   }

   private val font by lazy { BitmapFont() }
   private val batch by lazy {
      SpriteBatch().apply {
         color = Color.WHITE
      }
   }

   private fun ShapeRenderer.draw(point: Point) {
      this.point(point.x, point.y, 0f)
   }

   private val shapeRenderer by lazy {
      ShapeRenderer()
   }

   override fun render(delta: Float) {
      World.calc(delta)
      for (comet in comets) {
         comet.addPoint()
      }
      batch.use {
         font.draw(it, "pX = ${World.pX}", 700f, 580f)
         font.draw(it, "pY = ${World.pY}", 700f, 560f)
      }
      shapeRenderer.use(ShapeRenderer.ShapeType.Point) {
         for (comet in comets) {
            it.color = comet.color
            for (point in comet.points) {
               it.draw(point)
            }
         }
      }
      shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
         for (comet in comets) {
            it.color = Color.YELLOW
            val point = comet.points.last()
            it.circle(point.x, point.y, 3f)
         }
      }
   }

   override fun dispose() {
      font.dispose()
      batch.dispose()
   }
}

class Comet(val body: Body, val color: Color, length: Int) {
   fun addPoint() {
      queue += body.toPoint
   }

   private val queue = LimitedQueue<Point>(length)
   val points get() = queue.asSequence()
}
