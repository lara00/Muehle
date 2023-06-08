package de.htwg.se.Muehle.aview.gui

import org.scalatest._
import org.scalatest.concurrent.Eventually
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import de.htwg.se.Muehle.controller.Controller
import de.htwg.se.Muehle.model.{PlayerList, GameStap, Field, HumanPlayer}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import javax.swing.SwingUtilities
import scala.swing.event.ActionEvent

class SwingGuiSpec extends AnyFlatSpec with Matchers {
  "SwingGui" should "update player statistics and game stand label" in {
    val controller = Controller(
      GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4)),
      HumanPlayer()
    )
    controller.gamesize = 4
    val swingGui = new SwingGui(controller, true)
    swingGui.gameStandLabel.text shouldBe "WHITE, press on a field to place a stone."

    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = {
        val mill = MillField(controller)
        swingGui.millField.buttonPosition_(1).doClick()
        swingGui.gameStandLabel.text shouldBe "BLACK, press on a field to place a stone."
        swingGui.millField.buttonPosition_(2).doClick()
        swingGui.millField.buttonPosition_(10).doClick()
        swingGui.gameStandLabel.text shouldBe "BLACK, press on a field to place a stone."
        swingGui.millField.buttonPosition_(3).doClick()
        swingGui.gameStandLabel.text shouldBe "WHITE, press on a field to place a stone."
        swingGui.millField.buttonPosition_(22).doClick()
        swingGui.gameStandLabel.text shouldBe "WHITE, delete a stone."
        swingGui.millField.buttonPosition_(1).doClick()
        swingGui.millField.buttonPosition_(2).doClick()
        swingGui.millField.buttonPosition_(15).doClick()
        swingGui.gameStandLabel.text shouldBe "WHITE, press on a field to place a stone."
        swingGui.millField.buttonPosition_(23).doClick()
        swingGui.millField.buttonPosition_(17).doClick()
        swingGui.gameStandLabel.text shouldBe "WHITE, click on the field where you want to place the stone, and then click on the stone you want to move."
        swingGui.millField.buttonPosition_(22).doClick()
        swingGui.millField.buttonPosition_(10).doClick()
        swingGui.millField.buttonPosition_(1).doClick()
        swingGui.millField.buttonPosition_(1).doClick()
      }
    })
  }
}
