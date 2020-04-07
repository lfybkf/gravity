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
import kotlin.math.pow

class MyGame : KtxApplicationAdapter {
   private var isGamePaused = false
   private val comets = comets()
   private val font by lazy { BitmapFont() }
   private val batch by lazy { SpriteBatch() }

   private fun comets(): List<Comet> {
      val length = 5000
      val result = mutableListOf<Comet>()
      result += Comet(Body(100.0, World.percent(50, 50)), Color.RED, length)
      result += Comet(Body(1.0, World.percent(30, 50), 0 v2 20), Color.GREEN, length)
//   result += Comet(Body(2.0, World.percent(70, 80), Vector(-10, 5)), Color.FIREBRICK, length)
      return result
   }

   override fun create() {
      World.g = 2500.0
      World.t = 2

      comets.forEach { comet ->
         World += comet.body
      }
      World.storeImpulse()
   }

   private fun ShapeRenderer.draw(point: Point) {
      this.point(point.x, point.y, 0f)
   }

   private val shapeRenderer by lazy {
      ShapeRenderer()
   }

   override fun render() {
      val deltaTime = Gdx.graphics.deltaTime
      doInput()
      if (!isGamePaused) {
         doLogic(deltaTime)
         doDraw()
      }
   }

   private val display = Display()

   private fun doDraw() {
      clearScreen(0f, 0f, 0f, 0f)
      batch.use {
         display.show(it)
      }
      shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
         it.color = Color.YELLOW
         for (comet in comets) {
            val point = comet.points.last()
            it.circle(point.x, point.y, 3 * comet.body.m.pow(0.3).toFloat())
         }

         it.color = Color.WHITE
         val center = World.center
         it.rect(center.x.toFloat(), center.y.toFloat(), 15f, 15f)
      }
      shapeRenderer.use(ShapeRenderer.ShapeType.Point) {
         for (comet in comets) {
            it.color = comet.color
            for (point in comet.points) {
               it.draw(point)
            }
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

   inner class Display {
      private val shift = 30
      private val top by lazy { World.height - 50f }
      private val right by lazy { World.width - 200f }

      fun show(batch: SpriteBatch) {
         for ((i, comet) in comets.withIndex()) {
            font.color = comet.color
            font.draw(batch, comet.info, right, top - shift * i)
         }
      }
   }
}
