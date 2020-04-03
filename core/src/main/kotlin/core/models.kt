package core

import kotlin.math.pow
import kotlin.math.sqrt

object World {
   var g = 32146.8
   private val bodies = mutableListOf<Body>()
   private fun otherBodies(body: Body): List<Body> = bodies.filter { it != body }

   operator fun plusAssign(body: Body) {
      bodies += body
   }

   fun calc(dt: Float) {
      for (body in bodies) {
         if (body.isMovable) {
            body.calc(dt)
         }
      }
   }

   private fun Body.calcForce(): Vector {
      var vectorX = 0.0
      var vectorY = 0.0
      for (another in otherBodies(this)) {
         val distance3 = distance(another).pow(3)
         vectorX += g * (another.x - x) / distance3
         vectorY += g * (another.y - y) / distance3
      }
      return Vector(vectorX, vectorY)
   }

   private fun Body.calcVelocity(dt: Float) {
      val force:Vector = calcForce()
      vX += force.x * dt / m
      vY += force.y * dt / m
   }

   private fun Body.calc(dt: Float) {
      calcVelocity(dt)
      x += vX * dt
      y += vY * dt
   }
}

class Point(val x: Float, val y: Float)
class Vector(val x: Double, val y: Double)

open class Body(val m: Double, var x: Double, var y: Double, var vX: Double = 0.0, var vY: Double = 0.0, val isMovable: Boolean = true) {
   val toPoint
      get() = Point(x.toFloat(), y.toFloat())
   fun distance(another: Body) = sqrt(x.minus(another.x).pow(2) + y.minus(another.y).pow(2))
}