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
import de.htwg.se.Muehle.model.Stone
import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap
import de.htwg.se.Muehle.Default.given

class TuiSpec extends AnyWordSpec with Matchers {
  val controller =Controller()
  val tui = Tui()
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
        val tui = new Tui()
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
      val input = new java.io.StringReader("2\n")
      val tui = new Tui()
      tui.controller.put(1,-1)
      tui.controller.put(2,-1)
      tui.controller.put(10,-1)
      tui.controller.put(3,-1)
      Console.withIn(input) {
        tui.controller.put(22,-1)
      }
      //tui.controller.playername should be(Stone.Black)
    }
  }
}
