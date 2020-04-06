package core

import com.badlogic.gdx.graphics.Color
import lib.LimitedQueue

class Comet(val body: Body, val color: Color, length: Int) {
   fun addPoint() {
      queue += body.toPoint
   }

   private val queue = LimitedQueue<Point>(length)
   val points get() = queue.asSequence()
}

fun comets(): List<Comet> {
   val length = 5000
   val result = mutableListOf<Comet>()
   result += Comet(Body(100.0,  World.percent(50, 50)), Color.RED, length)
   result += Comet(Body(1.0, World.percent(20, 50), 0 v2 50), Color.GREEN, length)
   result += Comet(Body(2.0, World.percent(70, 80), Vector(-10, 5)), Color.FIREBRICK, length)
   return result
}