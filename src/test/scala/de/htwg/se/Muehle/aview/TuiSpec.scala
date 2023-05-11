package de.htwg.se.Muehle
package aview

import model.{Field, Stone, PlayerList, Player}
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
      val controller = Controller(field, players.getFirstPlayer, players)
      val tui = Tui(controller)
      "return true" in {
        tui.analyseInput("n3") shouldBe true
      }
      "return false" in {
        tui.analyseInput("25") shouldBe false
      }
      "return  false when end game" in {
        tui.analyseInput("q") shouldBe false
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
