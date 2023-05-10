package de.htwg.se.Muehle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.{Player, PlayerList, Stone}

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
    "print the stones to set for each player" in {
      val players = List(Player(Stone.White, 2, 0), Player(Stone.Black, 3, 0))
      val playerList = PlayerList(players)
      val printed = playerList.printStonesToSet()
      printed should be(
        "WHITE Player: Stone to set: W W" + "\n" +
          "BLACK Player: Stone to set: B B B" + "\n"
      )
    }
    "created with a given input" should {
      "create a list of two players with the given input and initial score of 0" in {
        val input = 3
        val playerList = PlayerList(input)
        playerList.players should have size 2
        playerList.players(0) should be(Player(Stone.White, input, 0))
        playerList.players(1) should be(Player(Stone.Black, input, 0))
      }
    }
  }
}
