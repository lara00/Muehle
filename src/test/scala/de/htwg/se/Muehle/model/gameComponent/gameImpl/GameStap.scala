package de.htwg.se.Muehle.model.gameComponent.gameImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.playerComponent.playerImpl.Player
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.Default.given
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.MillEvents

class GameStapSpec extends AnyWordSpec with Matchers {
  "A GameStap" when {
    val gameStap = given_IGameStap

    "gplayer" should {
      "return the current player" in {
        gameStap.gplayer should be(Player(Stone.White, 4, 0))
      }
    }

    "gplayerlist" should {
      "return the player list" in {
        gameStap.gplayerlist should be(PlayerList(4))
      }
    }

    "playername" should {
      "return the name of the current player" in {
        gameStap.playername should be(Stone.White)
      }
    }

    "gfield" should {
      "return the current field" in {
        gameStap.gfield should be(given_IField)
      }
    }

    "stonesofaktiveplayer" should {
      "return the number of stones the active player has to put" in {
        gameStap.stonesofaktiveplayer should be(4)
      }
    }

    "getNextPlayer" should {
      "return the next player in the player list" in {
        gameStap.getNextPlayer should be(Player(Stone.Black, 4, 0))
      }
    }

    "timetoSetMoveJumporMill" should {
      "test SetStone Event" in {
        val (updatedStap, moveEvent) = gameStap.timetoSetMoveJumporMill(1, -1)
        updatedStap shouldBe a[GameStap]
        updatedStap.gfield should be(given_IField.setStone(1, Stone.White))
        updatedStap.gplayer should be(Player(Stone.Black, 4, 0))
        moveEvent should be(MoveEvents.SetStone)
      }
      "test MoveStone Event" in {
        val f1 = given_IField.setStone(2, Stone.White).setStone(3, Stone.White).setStone(4, Stone.White)
        val p1 = PlayerList(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4))
        val g1 = GameStap(f1, p1.getFirstPlayer, p1)
        val (updatedStap1, moveEvent1) = g1.timetoSetMoveJumporMill(15, 3)
        updatedStap1.gfield should be(given_IField.setStone(2, Stone.White).setStone(15, Stone.White).setStone(4, Stone.White))
        updatedStap1.gplayer should be(Player(Stone.Black, 0, 4))
        moveEvent1 should be(MoveEvents.MoveStone)
      }
      "test SetStone_Mill Event" in {
        val f2 = given_IField.setStone(2, Stone.White).setStone(3, Stone.White).setStone(4, Stone.White)
        val p2 = PlayerList(Player(Stone.White, 1, 4), Player(Stone.Black, 0, 4))
        val g2 = GameStap(f2, p2.getFirstPlayer, p2)
        val (updatedStap2, moveEvent2) = g2.timetoSetMoveJumporMill(1, -1)
        updatedStap2.gfield should be(given_IField.setStone(1, Stone.White).setStone(2, Stone.White).setStone(3, Stone.White).setStone(4, Stone.White))
        updatedStap2.gplayer should be(Player(Stone.White, 0, 5))
        moveEvent2 should be(MoveEvents.SetStone_Mill)
      }
      "test MoveStone_Mill Event" in {
        val f3 = given_IField.setStone(2, Stone.White).setStone(3, Stone.White).setStone(10, Stone.White)
        val p3 = PlayerList(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4))
        val g3 = GameStap(f3, p3.getFirstPlayer, p3)
        val (updatedStap3, moveEvent3) = g3.timetoSetMoveJumporMill(1, 10)
        updatedStap3.gfield should be(given_IField.setStone(2, Stone.White).setStone(1, Stone.White).setStone(3, Stone.White))
        updatedStap3.gplayer should be(Player(Stone.White, 0, 4))
        moveEvent3 should be(MoveEvents.MoveStone_Mill)
      }
      "test NoMove Event" in {
        val f4 = given_IField.setStone(2, Stone.White)
        val p4 = PlayerList(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4))
        val g4 = GameStap(f4, p4.getFirstPlayer, p4)
        val (updatedStap4, moveEvent4) = g4.timetoSetMoveJumporMill(2, 2)
        moveEvent4 should be(MoveEvents.NoMove)
      }
    }

    "handleMill" should {
      "test DeleteStone Event" in {
        val f1 = given_IField.setStone(2, Stone.White).setStone(1, Stone.White).setStone(3, Stone.White).setStone(4, Stone.Black)
        val p1 = given_IPlayer.pplayerList(4)
        val g1 = GameStap(f1, p1.getFirstPlayer, p1)
        val (updatedStap, millEvent) = g1.handleMill(4)
        millEvent should be(MillEvents.DeleteStone)
      }
      "test Endgame after DeleteStone" in {
        val f2 = given_IField.setStone(2, Stone.White).setStone(1, Stone.White).setStone(3, Stone.White).setStone(4, Stone.Black)
        val p2 = PlayerList(Player(Stone.White, 0, 3), Player(Stone.Black, 0, 3))
        val g2 = GameStap(f2, p2.getFirstPlayer, p2)
        val (updatedStap1, millEvent1) = g2.handleMill(4)
        millEvent1 should be(MillEvents.EndGame)
      }
    }
  }
}