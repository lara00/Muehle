package de.htwg.se.Muehle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.playerComponent.Player

class PlayerListSpec extends AnyWordSpec with Matchers {
  "PlayerList" should {
    "return the first player" in {
      val players = List(Player(Stone.White, 2, 0), Player(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      playerList.getFirstPlayer should be(Player(Stone.White, 2, 0))
    }

    "return the next player" in {
      val players = List(Player(Stone.White, 2, 0), Player(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      playerList.getNextPlayer(Player(Stone.White, 2, 0)) should be(
        Player(Stone.Black, 2, 0)
      )
      playerList.getNextPlayer(Player(Stone.Black, 2, 0)) should be(
        Player(Stone.White, 2, 0)
      )
    }

    "update the stones in the field for the active player" in {
      val players = List(Player(Stone.White, 2, 0), Player(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      val updatedPlayerList =
        playerList.updateStonesInField(Player(Stone.White, 2, 0))
      updatedPlayerList should be(
        PlayerList(List(Player(Stone.White, 1, 1), Player(Stone.Black, 2, 0)))
      )
    }

    "check if the next player is not allowed to delete a stone" in {
      val players = List(Player(Stone.White, 2, 0), Player(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      val activePlayer = Player(Stone.White, 2, 0)
      val allowedToDelete = playerList.allowedtodeleteastone(activePlayer)
      allowedToDelete should be(true)
    }

    "check if there are three stones on the field for the opponent player" in {
      val players = List(Player(Stone.White, 2, 0), Player(Stone.Black, 3, 0))
      val playerList = PlayerList(players)
      val activePlayer = Player(Stone.White, 2, 0)
      val threeStonesOnField = playerList.threeStonesontheField(activePlayer)
      threeStonesOnField should be(true)
    }

    "update the stones after a mill for the active player" in {
      val players = List(Player(Stone.White, 1, 1), Player(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      val activePlayer = Player(Stone.White, 1, 1)
      val updatedPlayerList = playerList.updateStonesafterMill(activePlayer)
      updatedPlayerList should be(
        PlayerList(List(Player(Stone.White, 1, 0), Player(Stone.Black, 2, 0)))
      )
    }
  }
}
