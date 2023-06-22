package de.htwg.se.Muehle.model.playerComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerComponent.IPlayer
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.Default.given

class PlayerSpec extends AnyWordSpec with Matchers {
  "name" should {
    "be a Stone" in {
      val player = given_IPlayer.pplayer(Stone.White, 0, 0)
      player.pname shouldBe Stone.White
    }
  }
  
  "incrementStoneintheField" should {
    "increment the stoneintheField field by 1" in {
      val player = given_IPlayer.pplayer(Stone.White, 0, 0)
      val newPlayer = player.incrementStoneintheField
      newPlayer.pstoneinField shouldBe 1
    }
  }

  "decrementStoneintheField" should {
    "decrement the stoneintheField field by 1" in {
      val player = given_IPlayer.pplayer(Stone.White, 0, 1)
      val newPlayer = player.decrementStoneintheField
      newPlayer.pstoneinField shouldBe 0
    }
  }

  "setstone" should {
    "return a new Field with the specified value at the specified position" in {
      val field = given_IField
      val player1 = given_IPlayer.pplayer(Stone.White, 0, 0)
      val newField = player1.setstone(field, player1, 1)
      newField.stones_field(1) shouldBe Stone.White
    }
  }

  "stonetoputinthefield" should {
    "decrease the stonetoput field by 1" in {
      val player = given_IPlayer.pplayer(Stone.White, 2, 0)
      val newPlayer = player.stonetoputinthefield
      newPlayer.pstonetoput shouldBe 1
    }
  }
}
