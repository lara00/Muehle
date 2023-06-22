package de.htwg.se.Muehle
package aview.gui

import scala.swing.event.ButtonClicked
import scala.swing._
import de.htwg.se.Muehle.controller.controllerComponent.IController

class SettingsDialog(controller: IController) extends Dialog:
  title = "Settings"
  val sizes = Seq(3, 4, 5, 6, 7, 8, 9)
  val singleplayer = Seq(false, true)

  val buttons = sizes.flatMap { size =>
    singleplayer.map { single =>
      val radioButton = new RadioButton(s"${if (single) "Singel " else ""} Game ($size x $size)")
      radioButton.reactions += { case ButtonClicked(_) => controller.bildGameSet(size, single)
      }
      radioButton
    }}

  contents = new BoxPanel(Orientation.Vertical):
    contents ++= buttons