package de.htwg.se.Muehle
package aview
package gui

import java.awt.Color

import scala.swing.event._
import scala.swing._
import scalafx.scene.layout.Pane

import util.Event
import util.Observer
import controller.controllerComponent.IController

class SwingGui(var controller: IController) extends Frame with Observer {
  controller.add(this)
  title = "MILL"
  open()

  val millField = new MillField(controller)
  val gameStand = new Label():
    font = new Font(font.getName, 6, 20)

  val mainPanel = new BoxPanel(Orientation.Vertical):
    contents += gameStand
    contents += ShowStones(controller).createBoxPanel(Color.white,0,1)
    contents += millField
    contents += ShowStones(controller).createBoxPanel(Color.black,0,2)
  contents = mainPanel
  
  menuBar = new MenuBar:
      contents += new Menu("Settings"):
        contents += new MenuItem(Action("Open Settings")(new SettingsDialog(controller).visible = true))
  
  pack()
  visible = true
  redraw

  override def open(): Unit =
    super.open()
    listenTo(this)
    reactions += {
    case WindowClosing(_) => controller.quit("")
    }

  override def update(e: util.Event): Unit = e match 
    case Event.Quit => QuitConfirmationDialog(controller).showQuitConfirmationDialog
    case Event.Status => redraw
    case Event.Mill => aktualiseMill

  private def aktualiseMill: Unit =
    gameStand.text  = s"${controller.playername}, delete a stone."
    gameStand.revalidate()
    gameStand.repaint()
    millField.ismill = true

  private def redraw: Unit =
    millField.update(controller)
    gameStand.text = controller.getGameStandLabelText
    mainPanel.revalidate()
    mainPanel.repaint()
}