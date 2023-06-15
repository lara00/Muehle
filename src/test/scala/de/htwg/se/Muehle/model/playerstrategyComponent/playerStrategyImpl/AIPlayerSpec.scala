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
    "using a MockAIPlayer" should {
      val aiPlayer = new AIPlayer()

      "a move without mill" in {
        val gameStap: IGameStap = GameStap(given_IField, PlayerList(4).getFirstPlayer, PlayerList(4))
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 3, -1)
        moveEvent should be(MoveEvents.SetStone)
      }

      "make a move without mill" in {
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))
        val gameStap: IGameStap = GameStap(given_IField, p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 3, 2)
        moveEvent should be(MoveEvents.NoMove)
      }

      "make a move without" in {
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))
        val gameStap: IGameStap = GameStap(given_IField.setStone(3, Stone.White).setStone(24, Stone.Black), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 2, 3)
        moveEvent should be(MoveEvents.MoveStone)
      }

      "make a move with" in {
        val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 3), given_IPlayer.pplayer(Stone.Black, 0, 3))
        val gameStap: IGameStap = GameStap(given_IField.setStone(3, Stone.White).setStone(24, Stone.Black), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 2, 3)
        moveEvent should be(MoveEvents.MoveStone)
      }
      /*
      "AIPlayer make a move" in {
      val aiPlayer = new AIPlayer()
      val p1 = PlayerList(given_IPlayer.pplayer(Stone.White, 0, 4), given_IPlayer.pplayer(Stone.Black, 0, 4))
      aiPlayer.aimuhle = true
      aiPlayer.millgamestap = GameStap(
      given_IField.setStone(3, Stone.White).setStone(24, Stone.Black)
      .setStone(1, Stone.White).setStone(2, Stone.White),
      given_IPlayer.pplayer(Stone.Black, 0, 3),
      p1
      )

      val (newGameStap, moveEvent) = aiPlayer.makeMove(aiPlayer.millgamestap, 1, -1)
      //moveEvent should be(MoveEvents.SetStone)
    }
        */
    }
  }
}
