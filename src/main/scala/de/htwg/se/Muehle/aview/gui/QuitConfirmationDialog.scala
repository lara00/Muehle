package de.htwg.se.Muehle
package aview.gui
import controller.Controller

import scala.swing._
import scala.swing.event.ButtonClicked

class QuitConfirmationDialog(controller: Controller) extends Dialog:
  title = "Quit Game"
  modal = true

  peer.setUndecorated(true)

  val label = new Label("Do you want to end the game or start a new round?"):
    horizontalAlignment = Alignment.Center

  val endButton = new Button("End")
  val newRoundButton = new Button("New Round")

  contents = new BoxPanel(Orientation.Vertical):
    contents += label
    contents += new FlowPanel:
      contents += endButton
      contents += newRoundButton

  listenTo(endButton, newRoundButton)
  
  reactions += {
    case ButtonClicked(`endButton`) =>
      close(); System.exit(1)
    case ButtonClicked(`newRoundButton`) =>
      controller.bildGameSet(9, false); dispose()
      new SwingGui(controller)
  }

  def showQuitConfirmationDialog: Unit = 
    centerOnScreen()
    open()
