package de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers
import de.htwg.se.Muehle.Default.given_IGameStap
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer


class HumanPlayerSpec extends AnyWordSpec with Matchers {
  "name" should {
    "be a Stone" in {
        val playerstrategy = HumanPlayer()
        val g1 = GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4))
        val move = playerstrategy.makeMove(g1, 1, -1)
    }
  }
}