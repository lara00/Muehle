package de.htwg.se.Muehle.model

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MoveStoneSpec extends AnyWordSpec with Matchers {
  val player = Player(Stone.Black, 7, 0)
  val field = Field()
  "MoveStone" when {
    "player has stone on the board and stonetoput is 0" should {
      val playerWithStoneOnBoard = Player(Stone.Black, 0, 4)
      val newField = field.setStone(1, playerWithStoneOnBoard.name)
      val moveField = MoveStone(playerWithStoneOnBoard, newField, 2, 1)

      "place the stone at the specified position" in {
        moveField should be(field.setStone(2, Stone.Black))
      }
      "place the stone at the specified position, with is not allowed" in {
        val moveField = MoveStone(playerWithStoneOnBoard, newField, 3, 1)
        moveField should be(moveField)
      }
      "move stone from 10 to 11" in {
        val oldField = field.setStone(10, Stone.Black)
        val newField = MoveStone(player, oldField, 10, 11)
        newField should be(oldField)
      }
      "move stone from 11 to 10" in {
        val oldField = field.setStone(11, Stone.Black)
        val newField = MoveStone(player, oldField, 11, 10)
        newField should be(oldField)
      }
      "move stone from 12 to 11" in {
        val oldField = field.setStone(12, Stone.Black)
        val newField = MoveStone(player, oldField, 12, 11)
        newField should be(oldField)
      }
    }
  }
}
