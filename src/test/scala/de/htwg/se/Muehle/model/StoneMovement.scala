package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StoneMovementSpec extends AnyWordSpec with Matchers {
  val player = Player(Stone.Black, 7, 0)
  val field = Field()

  "StoneMovement" when {
    "given a 'to' position" should {
      "set a stone of the player's color at the given position" in {
        val newField = StoneMovement(player, field, 1, -1)
        newField.fields(1) should be(Stone.Black)
      }
    }
    "given a 'to' and 'from' position" should {
      "move a stone of the player's color from the 'from' position to the 'to' position" in {
        val oldField = field.setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField.fields(1) should be(Stone.Empty)
        newField.fields(2) should be(Stone.Black)
      }
      "not move a stone if the 'from' position is empty" in {
        val oldField = field.setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 3)
        newField should be(oldField)
      }
      "not move a stone if the 'to' position is already occupied by another stone" in {
        val oldField = field.setStone(1, Stone.Black).setStone(2, Stone.White)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(oldField)
      }
    }
  }
}
