package de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.MoveEvents
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.PlayerImpl.AIPlayer
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.Stone

class AIPlayerSpec extends AnyWordSpec with Matchers {
  "An AIPlayer" when {
    "using a MockAIPlayer" should {
      val aiPlayer = new AIPlayer()

      "make a move without mill" in {
        val gameStap: IGameStap = GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4))
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 3, -1)
        moveEvent should be (MoveEvents.SetStone)
      }
      "make a move without mil " in {
        val p1 = PlayerList(Player(Stone.White,0,4), Player(Stone.Black,0,4))
        val gameStap: IGameStap = GameStap(Field(), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 3, 2)
        moveEvent should be (MoveEvents.NoMove)
      }
      "make a move without" in {
        val p1 = PlayerList(Player(Stone.White,0,4), Player(Stone.Black,0,4))
        val gameStap: IGameStap = GameStap(Field().setStone(3, Stone.White).setStone(24, Stone.Black), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 2, 3)
        moveEvent should be (MoveEvents.MoveStone)
      }
      "make a move with" in {
        val p1 = PlayerList(Player(Stone.White,0,3), Player(Stone.Black,0,3))
        val gameStap: IGameStap = GameStap(Field().setStone(3, Stone.White).setStone(24, Stone.Black), p1.getFirstPlayer, p1)
        val (newGameStap, moveEvent) = aiPlayer.makeMove(gameStap, 2, 3)
        moveEvent should be (MoveEvents.MoveStone)
      }
      /*
      "make a move with mill" in {
        val mockAIPlayer = new MockAIPlayer()
        val gameStap: IGameStap = GameStap(Field().setStone(1, Stone.White).
        setStone(2, Stone.Black), Player(Stone.White,0,4), PlayerList(Player(Stone.White,0,4), Player(Stone.Black,0,4)))
        val (newGameStap, moveEvent) = mockAIPlayer.makeMove(gameStap, 10, 1)
        moveEvent should be (MoveEvents.SetStone)
      }
      */
    }
  }
}

