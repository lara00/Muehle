package de.htwg.se.Muehle
package aview

import model.{Field, Stone, PlayerList, Player, GameStap, AIPlayer}
import controller.Controller
import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.Muehle.util._
import java.io.StringReader
import java.io.ByteArrayOutputStream

class TuiSpec extends AnyWordSpec with Matchers {
  "The analyseInput function" when {
    "the player wants to end the game" should {
      val field = Field()
      val players = PlayerList(7)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      val tui = Tui(controller)
      "return false if exit" in {
        tui.analyseInput("q") shouldBe false
      }
      "return false" in {
        tui.analyseInput("25") shouldBe false
      }
      "return true if small Gamefield is ready" in {
        tui.analyseInput("sG") shouldBe true
      }
      "return true if mittle Gamefield is ready " in {
        tui.analyseInput("mG") shouldBe true
      }
      "return true if large Gamefield is ready" in {
        tui.analyseInput("lG") shouldBe true
      }
      "return true if small, singelplayer Gamefield is ready" in {
        tui.analyseInput("sSG") shouldBe true
      }
      "return true if mittle ,singelplayer Gamefield is ready " in {
        tui.analyseInput("mSG") shouldBe true
      }
      "return true if large  singelplayer Gamefield is ready" in {
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
}
