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

class SwingGui(using var controller: IController) extends Frame with Observer:
  controller.add(this)
  title = "MILL"

  val millField = new MillField(controller)
  val gameStand = new Label():
    font = new Font(font.getName, 6, 20)

  val mainPanel = new BoxPanel(Orientation.Vertical):
    contents += gameStand
    contents += ShowStones(controller).createBoxPanel(Color.white,controller.gamesize, 0)
    contents += millField
    contents += ShowStones(controller).createBoxPanel(Color.white,controller.gamesize, 0)
  contents = mainPanel
  
  menuBar = new MenuBar:
      contents += new Menu("Settings"):
        contents += new MenuItem(Action("Open Settings")(new SettingsDialog(controller).visible = true))
  
  pack()

  override def open(): Unit =
    super.open()
    super.visible = true
    redraw
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
    millField.ismill = true

  private def redraw: Unit =
    val (white_set, white_delete, black_set, balck_delete) = controller.PlayerStatics
    mainPanel.contents(1) = ShowStones(controller).createBoxPanel(Color.white, white_set, controller.gamesize - (white_delete + white_set))
    mainPanel.contents(3) = ShowStones(controller).createBoxPanel(Color.white, black_set, controller.gamesize - (balck_delete + black_set))
    millField.update(controller)
    gameStand.text = controller.getGameStandLabelText