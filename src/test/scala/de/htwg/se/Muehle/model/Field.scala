package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model.{Field, Stone}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec with Matchers {
  val field = Field()
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
        newField.fields(1) should be(Stone.Black)
        field2.fields(1) should be(Stone.Black)
        field3.fields(1) should be(Stone.Black)
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
