package de.htwg.se.Muehle.model.playerComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.Stone

class PlayerSpec extends AnyWordSpec with Matchers {
  "name" should {
    "be a Stone" in {
      val player = Player(Stone.White, 0, 0)
      player.pname shouldBe Stone.White
    }
  }
  "incrementStoneintheField" should {
    "increment the stoneintheField field by 1" in {
      val player = Player(Stone.White, 0, 0)
      val newPlayer = player.incrementStoneintheField
      newPlayer.stoneintheField shouldBe 1
    }
  }

  "decrementStoneintheField" should {
    "decrement the stoneintheField field by 1" in {
      val player = Player(Stone.White, 0, 1)
      val newPlayer = player.decrementStoneintheField
      newPlayer.stoneintheField shouldBe 0
    }
  }
  "setstone" should {
    "return a new Field with the specified value at the specified position" in {
      val field = Field()
      val player1 = Player(Stone.White, 0, 0)
      val newField = player1.setstone(field, player1, 1)
      newField.fields(1) shouldBe Stone.White
    }
  }

  "stonetoputinthefield" should {
    "decrease the stonetoput field by 1" in {
      val player = Player(Stone.White, 2, 0)
      val newPlayer = player.stonetoputinthefield
      newPlayer.stonetoput shouldBe 1
    }
  }
}
