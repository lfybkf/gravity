package core

import com.badlogic.gdx.graphics.Color
import lib.LimitedQueue
import lib.recharge

class Comet(val body: Body, val color: Color, length: Int) {
   fun addPoint() {
      queue += body.toPoint
   }

   val info by recharge(100) {
      body.info
   }

   private val queue = LimitedQueue<Point>(length)
   val points get() = queue.asSequence()
}

