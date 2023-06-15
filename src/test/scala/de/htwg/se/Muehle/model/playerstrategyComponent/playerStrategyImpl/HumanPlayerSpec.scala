package de.htwg.se.Muehle
package model.playerstrategyComponent.playerStrategyImpl

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer
import de.htwg.se.Muehle.Default.given
import com.google.inject.Injector
import com.google.inject.Guice


class HumanPlayerSpec extends AnyWordSpec with Matchers {
  "name" should {
    "be a Stone" in {
        val playerstrategy = HumanPlayer()
        val injector: Injector = Guice.createInjector(new Module())
        val field = injector.getInstance(classOf[IField])
        val g1 = GameStap(field, PlayerList(4).getFirstPlayer, PlayerList(4))
        val move = playerstrategy.makeMove(g1, 1, -1)
    }
  }
}