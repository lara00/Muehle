package de.htwg.se.Muehle
package aview.gui

import scala.swing.event.ButtonClicked
import scala.swing._
import de.htwg.se.Muehle.controller.controllerComponent.IController

class SettingsDialog(controller: IController) extends Dialog:
  val sizes = Seq(3, 4, 5, 6, 7, 8, 9)
  val singleplayer = Seq(false, true)
  title = "Settings"

  val buttons = for {
    size <- sizes
    single <- singleplayer
  } yield {
    val radioButton = new RadioButton(s"${if (single) "Single " else ""}Game ($size x $size)")
    radioButton.reactions += { case ButtonClicked(_) => controller.bildGameSet(size, single) }
    radioButton
  }

  contents = new BoxPanel(Orientation.Vertical):
    contents ++= buttons
    contents += new Label("Select game size:")
    border = Swing.EmptyBorder(10)

  pack()
  centerOnScreen()