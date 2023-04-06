package de.htwg.se.Muehle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.se.Muehle.model.{Field, Stone}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec with Matchers {
  "A Field" when {
    "it" should {
      "have a size of 24" in {
        val field = Field()
        field.size should be(24)
      }
    }
    "initialize the fields map correctly" in {
      val field = Field()
      field.fields.keys should contain allOf (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24)
    }

    "have a size of 24" in {
      val field = Field()
      field.size should be(24)
    }
    "initialize the fields" in {
      val field = Field()
      val values = field.fields.values.toList
      values.length shouldEqual 24
      values should contain only Stone.Empty
    }
    "initialize the fields with numbers 1 to 24" in {
      val field = Field()
      for (i <- 1 to 24) {
        field.fields.contains(i) shouldEqual true
      }
    }

    "have the same default fields as a map with integers 1 to 24 as keys and Stone.Empty as values" in {
      val expectedMap = (1 to 24).map(i => i -> Stone.Empty).toMap
      val field = Field()
      field.fields shouldEqual expectedMap
    }
    "contain integers 1 to 24" in {
      val r = 1 to 24
      val expectedRange = Range(1, 25)
      r should contain theSameElementsAs expectedRange
    }
    "modified using setField" should {
      "return a new Field with the specified value at the specified position" in {
        val field = Field()
        val newField = field.setField(1, Stone.Black)
        newField.fields(1) should be(Stone.Black)
      }
      "not modify the original Field" in {
        val field = Field()
        val newField = field.setField(1, Stone.Black)
        field.fields(1) should be(Stone.Empty)
      }
    }
    "printed" should {
      "display empty stones in all fields" in {
        val field = Field()
        field.printfeld() should be(
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
