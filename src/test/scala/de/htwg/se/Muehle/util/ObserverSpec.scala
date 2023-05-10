package de.htwg.se.Muehle.util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ObservableSpec extends AnyFlatSpec with Matchers {
  it should "notify all subscribers of an event" in {
    val observable = new Observable {}
    var eventFired = false
    val observer1 = new Observer {
      def update(e: Event): Unit = {
        if (e == Event.Set) eventFired = true
      }
    }
    observable.add(observer1)
    observable.notifyObservers(Event.Set)
    eventFired shouldBe true
  }
}
