package core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.app.KtxApplicationAdapter
import ktx.app.clearScreen
import ktx.graphics.use
import lib.LimitedQueue

class MyGame : KtxApplicationAdapter {
   private var isGamePaused = false
   private val length = 1000
   private val comets = mutableListOf<Comet>()
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

   override fun create() {
      World.g = 70000.0
      comets += Comet(Body(1.0, 100.0, 100.0, 10.0, 5.0), Color.RED, length)
      comets += Comet(Body(2.0, 300.0, 400.0, -5.0, -2.5), Color.GREEN, length)
      comets += Comet(Body(1.0, 400.0, 300.0, 5.0, -5.0), Color.BLUE, length)

      comets.forEach {comet ->
         World += comet.body
      }
   }

   override fun render() {
      val deltaTime = Gdx.graphics.deltaTime
      doInput()
      if (!isGamePaused) {
         doLogic(deltaTime)
         doDraw()
      }
   }

   private fun doDraw() {
      clearScreen(0f,0f,0f,0f)
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

   private fun doLogic(dt: Float) {
      World.calc(dt)
      for (comet in comets) {
         comet.addPoint()
      }
   }

   private fun doInput() {
      if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
         isGamePaused = !isGamePaused
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
