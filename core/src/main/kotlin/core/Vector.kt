package core

import kotlin.math.pow
import kotlin.math.sqrt

infix fun Double.v2(y: Double) = Vector(this, y)
infix fun Int.v2(y: Int) = Vector(this.toDouble(), y.toDouble())

class Vector(var x: Double = 0.0, var y: Double = 0.0) {
   constructor(x:Int, y:Int):this(x.toDouble(), y.toDouble())
   operator fun plus(other: Vector) = Vector(this.x + other.x, this.y + other.y)
   operator fun minus(other: Vector) = Vector(this.x - other.x, this.y - other.y)
   operator fun plusAssign(other: Vector) {
      x += other.x
      y += other.y
   }

   operator fun minusAssign(other: Vector) {
      x -= other.x
      y -= other.y
   }

   operator fun divAssign(value: Double) {
      x /= value
      y /= value
   }

   operator fun divAssign(value: Int) {
      x /= value
      y /= value
   }

   operator fun timesAssign(value: Double) {
      x *= value
      y *= value
   }

   operator fun timesAssign(value: Int) {
      x *= value
      y *= value
   }

   val length get() = sqrt(x.pow(2) + y.pow(2))
   val info get() = "(${x.format(2)}:${y.format(2)})"
}