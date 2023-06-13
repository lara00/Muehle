package de.htwg.se.Muehle
package aview.gui

import scala.swing._
import scala.swing.event.ButtonClicked
import controller.controllerComponent.IController

class QuitConfirmationDialog(controller: IController) extends Dialog:
  title = "Quit Game"

  peer.setUndecorated(true)

  contents = new BoxPanel(Orientation.Vertical):
    contents += new Label("Do you want to end the game or start a new round?")
    contents += new FlowPanel:
      val endButton = new Button("End")
      val newRoundButton = new Button("New Round")
      contents += endButton
      contents += newRoundButton
      listenTo(endButton, newRoundButton)
        reactions += {
          case ButtonClicked(`endButton`) => close(); System.exit(1)
          case ButtonClicked(`newRoundButton`) => controller.bildGameSet(9, false); close()
      }

  def showQuitConfirmationDialog: Unit = 
    centerOnScreen()
    open()
