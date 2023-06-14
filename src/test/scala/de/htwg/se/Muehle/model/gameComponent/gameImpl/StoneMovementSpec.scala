package de.htwg.se.Muehle.model.gameComponent.gameImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerComponent.playerImpl.Player
import de.htwg.se.Muehle.model.gameComponent.gameImpl.StoneMovement
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.Default.given

class StoneMovementSpec extends AnyWordSpec with Matchers {
  "StoneMovement" should {
    "set a stone at the given position when the 'from' position is not given" in {
      val player = Player(Stone.Black, 7, 0)
      val field = given_IField
      val newField = StoneMovement(player, field, 1, -1)
      newField.stones_field(1) should be(Stone.Black)
    }

    "move a stone from the 'from' position to the 'to' position" when {
      "the 'from' position is not empty and the 'to' position is not occupied" in {
        val player = Player(Stone.Black, 7, 0)
        val oldField = given_IField.setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField.stones_field(1) should be(Stone.Empty)
        newField.stones_field(2) should be(Stone.Black)
      }

      "the 'from' position is empty" in {
        val player = Player(Stone.Black, 7, 0)
        val oldField = given_IField
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(oldField)
      }

      "the 'to' position is already occupied" in {
        val player = Player(Stone.Black, 7, 0)
        val oldField = given_IField.setStone(1, Stone.Black).setStone(2, Stone.White)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(oldField)
      }

      "the stone is trying to jump from position 1 to 2" in {
        val player = given_IPlayer.pplayer(Stone.Black, 3, 0)
        val oldField = given_IField.setStone(1, Stone.Black)
        val newField = StoneMovement(player, oldField, 2, 1)
        newField should be(given_IField.setStone(2, Stone.Black))
      }
    }

  }
}
