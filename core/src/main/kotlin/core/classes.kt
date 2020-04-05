package core

import com.badlogic.gdx.utils.Pool
import kotlin.math.pow
import kotlin.math.sqrt

class Point(val x: Float, val y: Float)

class Body(val m: Double, val position: Vector,
                val velocity: Vector = Vector(),
                val acceleration: Vector = Vector(),
                val isMovable: Boolean = true) {
   val massVector: Vector get() = Vector().apply {
      this += position
      this *= m
   }
   val toPoint
      get() = Point(position.x.toFloat(), position.y.toFloat())

   fun distance(other: Body) = sqrt(distanceX(other).pow(2) + distanceY(other).pow(2))
   fun distanceX(other: Body) = other.position.x - position.x
   fun distanceY(other: Body) = other.position.y - position.y
   fun changeImpulse(deltaImpulse:Vector) {
      velocity.x += deltaImpulse.x / m
      velocity.y += deltaImpulse.y / m
   }
}

object PoolVector : Pool<Vector>(100, 1000) {
   override fun newObject() = Vector()
   fun <T> use(f: Vector.() -> T): T {
      val vector = this.obtain()
      val result = vector.f()
      free(vector)
      return result
   }
}
