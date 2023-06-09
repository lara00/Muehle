package de.htwg.se.Muehle
package aview.gui

import scala.swing.event.ButtonClicked
import scala.swing._
import de.htwg.se.Muehle.controller.controllerComponent.IController

class SettingsDialog(controller: IController) extends Dialog:
  val sizes = Seq(3, 4, 5, 6, 7, 8, 9)
  val singleplayer = Seq(false, true)
  val sizeGroup = new ButtonGroup
  title = "Settings"
  
  val buttons = sizes.flatMap { size => singleplayer.map { single =>
    val prefix = single match { case true => "Single" case false => "" }
    val radioButton = new RadioButton(s"$prefix Game ($size x $size)")
    radioButton.reactions += { case ButtonClicked(_) => controller.bildGameSet(size, single) }
    radioButton }}
   
  sizeGroup.buttons ++= buttons
  
  contents = new BoxPanel(Orientation.Vertical):
    contents ++= buttons
     border = Swing.EmptyBorder(10) 
