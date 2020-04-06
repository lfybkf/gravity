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
import kotlin.math.roundToInt

class MyGame : KtxApplicationAdapter {
   private var isGamePaused = false
   private val comets = comets()
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
      World.g = 25000.0
      World.t = 1

      comets.forEach {comet ->
         World += comet.body
      }
      World.storeImpulse()
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
         font.draw(it, "pX = ${World.pX.roundToInt()}", World.width - 50f, World.height - 80f)
         font.draw(it, "pY = ${World.pY.roundToInt()}", World.width - 50f, World.height - 100f)
      }
      shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
         it.color = Color.YELLOW
         for (comet in comets) {
            val point = comet.points.last()
            it.circle(point.x, point.y, 3*comet.body.m.pow(0.3).toFloat())
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
}
