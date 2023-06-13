package de.htwg.se.Muehle.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.gameComponent.gameImpl.StoneMovement

class StoneMovementSpec extends AnyWordSpec with Matchers {
  "StoneMovement" should {
    "set a stone at the given position when the 'from' position is not given" in {
      val player = Player(Stone.Black, 7, 0)
      val field = Field()
      val newField = StoneMovement(player, field, 1, -1)
      newField.fields(1) should be(Stone.Black)
    }

    "move a stone from the 'from' position to the 'to' position" when {
      "the 'from' position is not empty and the 'to' position is not occupied" in {
        val player = Player(Stone.Black, 7, 0)
        val oldField = Field().setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField.fields(1) should be(Stone.Empty)
        newField.fields(2) should be(Stone.Black)
      }

      "the 'from' position is empty" in {
        val player = Player(Stone.Black, 7, 0)
        val oldField = Field()
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(oldField)
      }

      "the 'to' position is already occupied" in {
        val player = Player(Stone.Black, 7, 0)
        val oldField = Field().setStone(1, Stone.Black).setStone(2, Stone.White)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(oldField)
      }

      "the stone is trying to jump from position 1 to 2" in {
        val player = Player(Stone.Black, 3, 0)
        val oldField = Field().setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(Field().setStone(2, Stone.Black))
      }
    }

  }
}
