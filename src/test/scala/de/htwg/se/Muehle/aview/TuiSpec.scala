package de.htwg.se.Muehle
package aview

import java.io.{ByteArrayOutputStream, PrintStream, StringReader, StringWriter}

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import util.Event
import scala.util.Try
import scalafx.scene.text.FontWeight.Black
import de.htwg.se.Muehle.model.fieldComponent.Field
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.AIPlayer
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayer
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap

class TuiSpec extends AnyWordSpec with Matchers {
  val field = Field()
  val players = PlayerList(7)
  val controller =
    Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
  val tui = Tui(controller)
  "The analyseInput function" when {

    "the player wants to end the game" should {
      "return true if call redo" in {
        tui.analyseInput(Try("z")) shouldBe true
      }
      "return true if call undo" in {
        tui.analyseInput(Try("y")) shouldBe true
      }
      "return false if exit" in {
        tui.analyseInput(Try("q")) shouldBe false
      }
      "return false" in {
        tui.analyseInput(Try("25")) shouldBe false
      }
      "return true if small Gamefield is ready" in {
        tui.analyseInput(Try("sG")) shouldBe true
      }
      "return true if middle Gamefield is ready" in {
        tui.analyseInput(Try("mG")) shouldBe true
      }
      "return true if large Gamefield is ready" in {
        tui.analyseInput(Try("lG")) shouldBe true
      }
      "return true if small, singleplayer Gamefield is ready" in {
        tui.analyseInput(Try("sSG")) shouldBe true
      }
      "return true if middle, singleplayer Gamefield is ready" in {
        tui.analyseInput(Try("mSG")) shouldBe true
      }
      "return true if large, singleplayer Gamefield is ready" in {
        tui.analyseInput(Try("lSG")) shouldBe true
      }
      "put value and ask for another value on valid input" in {
        val mockInput = new java.io.StringReader("4\n")
        val tui = new Tui(controller)
        var isInputProcessed = false
        Console.withIn(mockInput) {
          isInputProcessed = tui.analyseInput(Try("3"))
        }
        isInputProcessed should be(true)
      }
    }
  }
  "update method" should {
    "print 'Mühle' and process input" in {
      val controller = Controller(
        GameStap(
          field.setStone(4, Stone.Black).setStone(6,Stone.Black).setStone(1,Stone.White),
          Player(Stone.Black, 0, 4),
          PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
        ),
        HumanPlayer()
      )
      val input = new java.io.StringReader("1\n")
      val tui = new Tui(controller)
      Console.withIn(input) {
        controller.put(5,-1)
      }
      controller.gamefield.gplayerlist should be(
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
      )
    }
  }
}
