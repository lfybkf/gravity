package lib

import java.util.*

class LimitedQueue<T>(private val limit:Int) : LinkedList<T>() {
   override fun add(element: T): Boolean {
      super.add(element)
      while (size > limit) {
         super.remove()
      }
      return true
   }

   override fun addAll(elements: Collection<T>): Boolean {
      super.addAll(elements)
      while (size > limit) {
         super.remove()
      }
      return true
   }

}