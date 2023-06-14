package de.htwg.se.Muehle.model.gameComponent.gameImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.gameComponent.gameImpl.Mill
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.Default.given

class MillSpec extends AnyFlatSpec with Matchers {
  "A Mill" should "detect a mill when it exists horizontal" in {
    val field = given_IField
      .setStone(1, Stone.White)
      .setStone(2, Stone.White)
      .setStone(3, Stone.White)
    val mill = new Mill(field)
    val result = mill.existsMill()
    result should be(true)
  }
    "A Mill" should "detect a mill when it exists vertical" in {
    val field = given_IField.setStone(1, Stone.White).setStone(10, Stone.White).setStone(22, Stone.White)
    val mill = new Mill(field)
    val result = mill.existsMill()
    result should be(true)
  }
  it should "not detect a mill when it doesn't exist" in {
    val field =given_IField
    val mill = new Mill(field)
    val result = mill.existsMill()
    result should be(false)
  }
}
