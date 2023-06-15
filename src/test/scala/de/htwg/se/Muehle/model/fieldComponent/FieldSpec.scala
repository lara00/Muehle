package de.htwg.se.Muehle
package model
package fieldComponent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.Default.given
import com.google.inject.Injector
import com.google.inject.Guice

class FieldSpec extends AnyWordSpec with Matchers {
  val injector: Injector = Guice.createInjector(new Module())
  val field = injector.getInstance(classOf[IField])
  "A Field" when {
    "its size is checked" should {
      "return 24" in {
        field.size should be(24)
      }
    }
    "modified using setField" should {
      val newField = field.setStone(1, Stone.Black)
      val field2 = newField.setStone(1, Stone.Black)
      val field3 = newField.setStone(1, Stone.White)
      "return a new Field with the specified value at the specified position" in {
        newField.stones_field(1) should be(Stone.Black)
        field2.stones_field(1) should be(Stone.Black)
        field3.stones_field(1) should be(Stone.Black)
      }
    }
    "isFieldValid" should {
      "return true for a valid integer within range" in {
        val validInt = "12"
        field.isFieldValid(validInt) shouldBe true
      }
      "return false for an integer above the maximum range" in {
        val invalidInt = "30"
        field.isFieldValid(invalidInt) shouldBe false
      }
      "return false for a non-integer input" in {
        val invalidInput = "abc"
        field.isFieldValid(invalidInput) shouldBe false
      }
    }
    "deleteStone" should {
      "return a new Field with the specified position set to Empty" in {
        val newField = field.setStone(1, Stone.Black)
        val updatedField = newField.deleteStone(1, Stone.Black)
        updatedField.stones_field(1) should be(Stone.Empty)
      }
      "return the same Field if a other Stone in the Field" in {
        val newField = field.setStone(1, Stone.White)
        val updatedField = newField.deleteStone(1, Stone.Black)
        updatedField.stones_field(1) should be(Stone.White)
      }
    }
    "movestone" should {
      "return a new Field with the specified stone moved to the new position" in {
        val newField = field.setStone(1, Stone.Black)
        val updatedField = newField.movestone(1, 2, Stone.Black)
        updatedField.stones_field(1) should be(Stone.Empty)
        updatedField.stones_field(2) should be(Stone.Black)
      }
      "return the same Field if the stone is not moved to an empty position" in {
        val newField = field.setStone(1, Stone.Black)
        val updatedField = newField.movestone(1, 2, Stone.Black)
        val sameField = updatedField.movestone(1, 2, Stone.Black)
        sameField should be(updatedField)
      }
      "return the same Field if the stone at the original position is not the specified value" in {
        val newField = field.setStone(1, Stone.Black)
        val sameField = newField.movestone(1, 2, Stone.White)
        sameField should be(newField)
      }
      "return the same Field if the new position is not empty" in {
        val newField = field.setStone(1, Stone.Black)
        val updatedField = newField.setStone(2, Stone.White)
        val sameField = updatedField.movestone(1, 2, Stone.Black)
        sameField should be(updatedField)
      }
    }
      "getStonePositions" should {
      "return the positions of stones of the specified type" in {
        val newField = field.setStone(1, Stone.Black)
        val whitePositions = newField.getWhiteStonePositions
        val blackPositions = newField.getBlackStonePositions
        
        whitePositions should be(Nil)
        blackPositions should be(List(1))
      }
    }
    "printed" should {
      "display empty stones in all fields" in {
        field.toString() should be(
          "EMPTY |       |       | EMPTY |       |       | EMPTY" + "\n" +
            "      | EMPTY |       | EMPTY |       | EMPTY |      " + "\n" +
            "      |       | EMPTY | EMPTY | EMPTY |       |      " + "\n" +
            "EMPTY | EMPTY | EMPTY |       | EMPTY | EMPTY | EMPTY" + "\n" +
            "      |       | EMPTY | EMPTY | EMPTY |       |      " + "\n" +
            "      | EMPTY |       | EMPTY |       | EMPTY |      " + "\n" +
            "EMPTY |       |       | EMPTY |       |       | EMPTY" + "\n"
        )
      }
    }
  }
}
