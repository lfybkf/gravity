package core

import kotlin.math.pow

enum class Potential { GRAVITY, SPRING, LINEAR}

object World {
   private val potential = Potential.GRAVITY
   var width: Int = 0
   var height: Int = 0
   private lateinit var initialImpulse: Vector
   var t = 1
   var g = 1.0
   private val bodies = mutableListOf<Body>()
   private fun otherBodies(body: Body): List<Body> = bodies.filter { it != body }
   val pX:Double get() = bodies.sumByDouble { it.m * it.velocity.x }
   val pY:Double get() = bodies.sumByDouble { it.m * it.velocity.y }

   operator fun plusAssign(body: Body) {
      bodies += body
   }

   fun calc(dt: Float) {
      for (body in bodies) {
         if (body.isMovable) {
            body.calc(dt * t)
         }
      }
      compensateImpulse()
   }

   /**
    * Without g and m
    */
   private fun Body.calcAcceleration() {
      for (other in otherBodies(this)) {
         when(potential) {
            Potential.GRAVITY -> {
               val distance3 = distance(other).pow(3)
               acceleration.x = other.m * distanceX(other) / distance3
               acceleration.y = other.m * distanceY(other) / distance3
            }
            Potential.SPRING -> {
               acceleration.x = 1.0
               acceleration.y = 1.0
            }
            Potential.LINEAR -> {

            }
         }

      }
   }

   private fun Body.calcVelocity(dt: Float) {
      velocity.x += acceleration.x * dt * g
      velocity.y += acceleration.y * dt * g
   }

   private fun Body.calc(dt: Float) {
      calcAcceleration()
      calcPosition(dt)
      calcVelocity(dt)
   }

   private fun compensateImpulse() {
      val deltaImpulse = initialImpulse - Vector(pX, pY)
      deltaImpulse /= bodies.size
      for (body in bodies) {
         body.changeImpulse(deltaImpulse)
      }
   }

   private fun Body.calcPosition(dt: Float) {
      position.x += velocity.x * dt
      position.y += velocity.y * dt
   }

   fun storeImpulse() {
      initialImpulse = Vector(pX, pY)
   }

   fun percent(placeX: Int, placeY: Int) = Vector(width * placeX * 0.01, height * placeY * 0.01)

   val center:Vector get() {
      val result = Vector()
      for (body in bodies) {
         result += body.massVector
      }
      val totalMass = bodies.sumByDouble { it.m }
      result /= totalMass
      return result
   }
}