package de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.AIPlayer
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerComponent.playerImpl.Player
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.Default.given

class AIPlayerSpec extends AnyWordSpec with Matchers {
  "An AIPlayer" when {
    "using an AIPlayer" should {
      val aiPlayer = new AIPlayer()

      "handle a move without forming a mill when setting a stone" in {
        val gameStap: IGameStap = GameStap(given_IField, PlayerList(4).getFirstPlayer, PlayerList(4))
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 3, -1)
        moveEvent should be(MoveEvents.SetStone)
      }

      "handle a move without forming a mill that fails" in {
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))
        val gameStap: IGameStap = GameStap(given_IField, p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 3, 2)
        moveEvent should be(MoveEvents.NoMove)
      }

      "handle a move without forming a mill to move a stone" in {
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))
        val gameStap: IGameStap = GameStap(given_IField.setStone(3, Stone.White).setStone(24, Stone.Black), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 2, 3)
        moveEvent should be(MoveEvents.MoveStone)
      }

      "handle a move without forming a mill to jump with a stone" in {
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 3), given_IPlayer.pplayer(Stone.Black, 0, 3))
        val gameStap: IGameStap = GameStap(given_IField.setStone(3, Stone.White).setStone(24, Stone.Black), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 2, 3)
        moveEvent should be(MoveEvents.MoveStone)
      }

      "handle a move if the player has a mill" in {
        val p2 = PlayerList(given_IPlayer.pplayer(Stone.White, 3, 1), given_IPlayer.pplayer(Stone.Black, 3, 1))
        aiPlayer.automove_withoutmill(given_IGameStap.newGamestap(
          given_IField.setStone(3, Stone.White).setStone(24, Stone.Black).setStone(24, Stone.Black)
            .setStone(1, Stone.White).setStone(2, Stone.White),
          given_IPlayer.pplayer(Stone.Black, 3, 1), p2), 1, -1)
      }

      "make a move as an AIPlayer when the AIPlayer has a mill" should {
        val aiPlayer = new AIPlayer()
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))
        "simulate AIPlayer having a mill" in {
          aiPlayer.aimuhle = true
          aiPlayer.millgamestap = given_IGameStap.newGamestap(given_IField.setStone(3, Stone.White)
            .setStone(24, Stone.Black).setStone(24, Stone.Black).setStone(1, Stone.White).setStone(2, Stone.White), p1.getFirstPlayer, p1)
          val (newGameStap, moveEvent) = aiPlayer.makeMove(given_IGameStap, 1, -1)
        }
        "simulate AIPlayer getting a mill" in {
          val p2 = PlayerList(given_IPlayer.pplayer(Stone.White, 3, 1), given_IPlayer.pplayer(Stone.Black, 3, 1))
          aiPlayer.millcheck(1, -1, given_IGameStap.newGamestap(
            given_IField.setStone(4, Stone.White).setStone(10, Stone.Black).setStone(22, Stone.Black)
              .setStone(7, Stone.White).setStone(2, Stone.White), given_IPlayer.pplayer(Stone.Black, 3, 1), p2))
        }
      }
    }
  }
}