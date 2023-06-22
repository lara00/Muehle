package de.htwg.se.Muehle.aview.gui

import de.htwg.se.Muehle.model.fieldComponent.IField

import javax.swing.SwingUtilities
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.Muehle.aview.gui
import de.htwg.se.Muehle.Default.given

import javax.swing.SwingUtilities
import scala.swing.event.ActionEvent
import de.htwg.se.Muehle.controller.controllerComponent.controllerBaseImpl.Controller
import java.awt.Graphics2D

class MillFieldSpec extends AnyFlatSpec with Matchers {
  "MillField" should "update player statistics and game stand label" in {
    val con = Controller()
    System.setProperty("java.awt.headless", "true")
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = {
        val millField = MillField(con)
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
        millField.update(con)
      }
    })
  }
}
