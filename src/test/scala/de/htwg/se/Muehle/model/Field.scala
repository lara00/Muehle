package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model.{Field, Stone}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec with Matchers {
  "A Field" when {
    "its size is checked" should {
      "return 24" in {
        val field = Field()
        field.size should be(24)
      }
    }
    "modified using setField" should {
      "return a new Field with the specified value at the specified position" in {
        val field = Field()
        val newField = field.setField(1, Stone.Black)
        newField.fields(1) should be(Stone.Black)
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
