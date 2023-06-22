package de.htwg.se.Muehle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.Default.given

class PlayerListSpec extends AnyWordSpec with Matchers {
  "PlayerList" should {
    "return the first player" in {
      val players = List(given_IPlayer.pplayer(Stone.White, 2, 0), given_IPlayer.pplayer(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      playerList.getFirstPlayer should be(given_IPlayer.pplayer(Stone.White, 2, 0))
    }

    "return the next player" in {
      val players = List(given_IPlayer.pplayer(Stone.White, 2, 0), given_IPlayer.pplayer(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      playerList.getNextPlayer(given_IPlayer.pplayer(Stone.White, 2, 0)) should be(given_IPlayer.pplayer(Stone.Black, 2, 0))
      playerList.getNextPlayer(given_IPlayer.pplayer(Stone.Black, 2, 0)) should be(given_IPlayer.pplayer(Stone.White, 2, 0))
    }

    "update the stones in the field for the active player" in {
      val players = List(given_IPlayer.pplayer(Stone.White, 2, 0), given_IPlayer.pplayer(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      val updatedPlayerList = playerList.updateStonesInField(given_IPlayer.pplayer(Stone.White, 2, 0))
      updatedPlayerList should be(PlayerList(List(given_IPlayer.pplayer(Stone.White, 1, 1), given_IPlayer.pplayer(Stone.Black, 2, 0))))
    }

    "check if the next player is not allowed to delete a stone" in {
      val players = List(given_IPlayer.pplayer(Stone.White, 2, 0), given_IPlayer.pplayer(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      val activePlayer = given_IPlayer.pplayer(Stone.White, 2, 0)
      val allowedToDelete = playerList.allowedtodeleteastone(activePlayer)
      allowedToDelete should be(true)
    }

    "check if there are three stones on the field for the opponent player" in {
      val players = List(given_IPlayer.pplayer(Stone.White, 2, 0), given_IPlayer.pplayer(Stone.Black, 3, 0))
      val playerList = PlayerList(players)
      val activePlayer = given_IPlayer.pplayer(Stone.White, 2, 0)
      val threeStonesOnField = playerList.threeStonesontheField(activePlayer)
      threeStonesOnField should be(true)
    }

    "update the stones after a mill for the active player" in {
      val players = List(given_IPlayer.pplayer(Stone.White, 1, 1), given_IPlayer.pplayer(Stone.Black, 2, 0))
      val playerList = PlayerList(players)
      val activePlayer = given_IPlayer.pplayer(Stone.White, 1, 1)
      val updatedPlayerList = playerList.updateStonesafterMill(activePlayer)
      updatedPlayerList should be(PlayerList(List(given_IPlayer.pplayer(Stone.White, 1, 0), given_IPlayer.pplayer(Stone.Black, 2, 0))))
    }
  }
}
