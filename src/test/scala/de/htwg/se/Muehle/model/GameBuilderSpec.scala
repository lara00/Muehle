package de.htwg.se.Muehle.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.*
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayerImpl.AIPlayer
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayerImpl.HumanPlayer
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.Default.given

class GamefieldBuilderTest extends AnyWordSpec with Matchers {
  "GamefieldBuilder" should {
    "build a Gamefield with specified stones to put and single gamer" in {
      val builder = new GamefieldBuilder()
      val gamefield = builder.addStonesToPut(5).addSingleGamer(true).build()
      gamefield.gamesetting.gplayer.pstonetoput shouldBe 5
      gamefield.gamestrategy shouldBe an[AIPlayer]
    }

    "build a Gamefield with default values when no parameters are specified" in {
      val builder = new GamefieldBuilder()
      val gamefield = builder.build()
      gamefield.gamesetting.gplayer.pstonetoput shouldBe 4
      gamefield.gamestrategy shouldBe a[HumanPlayer]
    }

    "build a Gamefield with specified single gamer" in {
      val builder = new GamefieldBuilder()
      val gamefield = builder.addSingleGamer(false).build()
      gamefield.gamestrategy shouldBe a[HumanPlayer]
    }

    "provide a string representation of the Gamefield" in {
      val gamesetting = GameStap(given_IField, PlayerList(9).getFirstPlayer, PlayerList(9))
      val gamestrategy = HumanPlayer()
      val gamefield = new Gamefield(gamesetting, gamestrategy)
      gamefield.toString contains "Gamefield(playerlist=[PlayerList(List(Player(WHITE, 9, 0), Player(BLACK, 9, 0))), singlegamer=HumanPlayer])"
      val aiplayerfield = new GamefieldBuilder().addStonesToPut(9).addSingleGamer(true).build()
      aiplayerfield.toString contains "Gamefield(playerlist=[PlayerList(List(Player(WHITE, 9, 0), Player(BLACK, 9, 0))), singlegamer=AIPlayer])"
    }
  }
}
