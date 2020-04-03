package lib

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class LimitedQueueTest {
   @Test
   fun test1() {
      val queue:Queue<String> = LimitedQueue<String>(3)
      queue += "A"
      queue += "B"
      queue += "C"
      queue += "D"
      println(queue)
      with(queue) {
         assertTrue(elementAt(0) == "B")
         assertTrue(elementAt(1) == "C")
         assertTrue(elementAt(2) == "D")
      }
   }

   @Test
   fun test2() {
      val queue:Queue<String> = LimitedQueue<String>(3)
      queue += listOf("A", "B", "C", "D", "E")
      println(queue)
      with(queue) {
         assertTrue(elementAt(0) == "C")
         assertTrue(elementAt(1) == "D")
         assertTrue(elementAt(2) == "E")
      }
   }
}