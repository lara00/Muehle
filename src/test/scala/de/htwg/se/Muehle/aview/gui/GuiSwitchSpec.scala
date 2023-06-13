package de.htwg.se.Muehle.aview.gui

import org.scalatest._
import org.scalatest.concurrent.Eventually
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import de.htwg.se.Muehle.model.fieldComponent.Field

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import javax.swing.SwingUtilities
import scala.swing.event.ActionEvent

import javax.swing.SwingUtilities
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.playerstrategyComponent.playerStrategyImpl.HumanPlayer
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.Muehle.model.gameComponent.gameImpl.GameStap

class SwingGuiSpec extends AnyFlatSpec with Matchers {
  "SwingGui" should "update player statistics and game stand label" in {
    val controller = Controller(
      GameStap(Field(), PlayerList(4).getFirstPlayer, PlayerList(4)),
      HumanPlayer()
    )
    controller.gamesize = 4

    System.setProperty("java.awt.headless", "true")

    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = {
        val millField = MillField(controller)
        millField.buttonPosition_(1).doClick()
        millField.buttonPosition_(2).doClick()
        millField.buttonPosition_(10).doClick()
        millField.buttonPosition_(3).doClick()
        millField.buttonPosition_(22).doClick()
        millField.buttonPosition_(1).doClick()
        millField.buttonPosition_(2).doClick()
        millField.buttonPosition_(15).doClick()
        millField.buttonPosition_(23).doClick()
        millField.buttonPosition_(17).doClick()
        millField.buttonPosition_(22).doClick()
        millField.buttonPosition_(10).doClick()
        millField.buttonPosition_(1).doClick()
        millField.buttonPosition_(1).doClick()
      }
    })
  }
}
