package de.htwg.se.Muehle.model.gameComponent.gameImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.Stone


class MoveStoneSpec extends AnyWordSpec with Matchers {
  val player = Player(Stone.Black, 7, 0)
  val field = Field()
  "MoveStone" when {
    "player has stone on the board and stonetoput is 0" should {
      val playerWithStoneOnBoard = Player(Stone.Black, 0, 4)
      val newField = field.setStone(1, playerWithStoneOnBoard.name)
      val moveField = StoneMovement(playerWithStoneOnBoard, newField, 2, 1)

      "place the stone at the specified position" in {
        moveField should be(field.setStone(2, Stone.Black))
      }
      "move stone from 10 to 11" in {
        val oldField = field.setStone(10, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 11, 10)
        newField should be(field.setStone(11, Stone.Black))
      }
      "move stone from 11 to 10" in {
        val oldField = field.setStone(11, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 10, 11)
        newField should be(field.setStone(10, Stone.Black))
      }
      "move stone from 12 to 11" in {
        val oldField = field.setStone(12, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 11, 12)
        newField should be(field.setStone(11, Stone.Black))
      }
      "move stone from 6 to 14" in {
        val oldField = field.setStone(6, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 14, 6)
        newField should be(field.setStone(14, Stone.Black))
      }
      "move stone from 2 to 5" in {
        val oldField = field.setStone(2, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 5, 2)
        newField should be(field.setStone(5, Stone.Black))
      }
      "move stone from 5 to 2" in {
        val oldField = field.setStone(5, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 2, 5)
        newField should be(field.setStone(2, Stone.Black))
      }
      "move stone from 8 to 5" in {
        val oldField = field.setStone(8, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 5, 8)
        newField should be(field.setStone(5, Stone.Black))
      }
      "move stone failed" in {
        val oldField = field.setStone(1, Stone.Black)
        val newField = StoneMovement(playerWithStoneOnBoard, oldField, 11, 1)
        newField should be(oldField)
      }
      "not jump with a stone from 1 to 2" in {
        val player = Player(Stone.Black, 3, 0)
        val oldField = field.setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(field.setStone(2, Stone.Black))
      }
    }
  }
}
