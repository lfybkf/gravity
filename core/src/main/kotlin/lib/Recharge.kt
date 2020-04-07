package lib

import kotlin.reflect.KProperty

fun <T> recharge(period:Int, work: () -> T) = Recharge(period, work)

class Recharge<out T>(private val period:Int, val rechargeFun: () -> T) {
   private var count = period
   private var currentValue:T = rechargeFun()

   operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
      if (count <= 0) {
         count = period
         currentValue = rechargeFun()
      } else {
         count--
      }
      return currentValue
   }
}