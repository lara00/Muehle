package de.htwg.se.Muehle.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.gameComponent.gameImpl.Mill

class MillSpec extends AnyFlatSpec with Matchers {
  "A Mill" should "detect a mill when it exists" in {
    val field = new Field()
      .setStone(1, Stone.White)
      .setStone(2, Stone.White)
      .setStone(3, Stone.White)
    val mill = new Mill(field)

    val result = mill.existsMill()
    result should be(true)
  }
  it should "not detect a mill when it doesn't exist" in {
    val field = new Field()
    val mill = new Mill(field)

    val result = mill.existsMill()

    result should be(false)
  }
}
