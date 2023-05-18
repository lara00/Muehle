package de.htwg.se.Muehle
package aview

import model.{Field, Stone, PlayerList, Player, GameStap, AIPlayer}
import java.io.{ByteArrayOutputStream, PrintStream, StringReader}
import controller.Controller
import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.util._
import java.io.StringWriter
import de.htwg.se.Muehle.model.HumanPlayer

class TuiSpec extends AnyWordSpec with Matchers {
  val field = Field()
  val players = PlayerList(7)
  val controller =
    Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
  val tui = Tui(controller)
  "The analyseInput function" when {

    "the player wants to end the game" should {
      "return false if exit" in {
        tui.analyseInput("q") shouldBe false
      }
      "return false" in {
        tui.analyseInput("25") shouldBe false
      }
      "return true if small Gamefield is ready" in {
        tui.analyseInput("sG") shouldBe true
      }
      "return true if middle Gamefield is ready" in {
        tui.analyseInput("mG") shouldBe true
      }
      "return true if large Gamefield is ready" in {
        tui.analyseInput("lG") shouldBe true
      }
      "return true if small, singleplayer Gamefield is ready" in {
        tui.analyseInput("sSG") shouldBe true
      }
      "return true if middle, singleplayer Gamefield is ready" in {
        tui.analyseInput("mSG") shouldBe true
      }
      "return true if large, singleplayer Gamefield is ready" in {
        tui.analyseInput("lSG") shouldBe true
      }
      "put value and ask for another value on valid input" in {
        val mockInput = new java.io.StringReader("4\n")
        val tui = new Tui(controller)
        var isInputProcessed = false
        Console.withIn(mockInput) {
          isInputProcessed = tui.analyseInput("3")
        }
        isInputProcessed should be(true)
      }
    }
  }
  "update method" should {
    "print 'Mühle' and process input" in {
      val controller = Controller(
        GameStap(
          field.setStone(5, Stone.Black),
          Player(Stone.White, 0, 4),
          PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
        ),
        HumanPlayer()
      )
      val input = new java.io.StringReader("5\n")
      Console.withIn(input) {
        val tui = new Tui(controller)
        tui.update(Event.IsMill)
      }
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 3)))
      )
    }
  }

}
