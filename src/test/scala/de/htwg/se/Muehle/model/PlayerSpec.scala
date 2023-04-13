package de.htwg.se.Muehle.model

import de.htwg.se.Muehle.model.{Field, Stone}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {
  "The incrementPlayedStone method" should {
    "increment the playedStone field by 1" in {
      val player = Player(Stone.White, 0, 0)
      val newPlayer = player.incrementPlayedStone
      newPlayer.playedStone should be(1)
    }
  }
  "The incrementStoneintheField method" should {
    "increment the stoneintheField field by 1" in {
      val player = Player(Stone.White, 0, 0)
      val newPlayer = player.incrementStoneintheField
      newPlayer.stoneintheField should be(1)
    }
  }
  "The decrementStoneintheField method" should {
    "decrement the stoneintheField field by 1" in {
      val player = Player(Stone.White, 0, 1)
      val newPlayer = player.decrementStoneintheField
      newPlayer.stoneintheField should be(0)
    }
  }

  "The toString method" should {
    "return a string in the expected format" in {
      val player = Player(Stone.White, 0, 0)
      player.toString() should be("Player(WHITE, 0, 0)")
    }
  }
  "Change Player" should {
    "return the new Player" in {
      val player1 = new Player(Stone.White, 0, 0)
      val player2 = new Player(Stone.Black, 0, 0)
      val players = List(player1, player2)

      val nextplayer = changePlayer(player1, players)
      nextplayer should be(player2)

      val nextplayer2 = changePlayer(player2, players)
      nextplayer2 should be(player1)
    }
  }
  "player setStone" should {
    "return a new Field with the specified value at the specified position" in {
      val field = Field()
      val player1 = new Player(Stone.White, 0, 0)
      val newField = setstone(field, player1, 1)
      newField.fields(1) should be(Stone.White)
    }
  }
}
