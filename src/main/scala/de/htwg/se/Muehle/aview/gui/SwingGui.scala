package de.htwg.se.Muehle
package aview
package gui

import java.awt.Color

import scala.swing.event._
import scala.swing._
import scalafx.scene.layout.Pane

import controller.Controller
import util.Event
import util.Observer

class SwingGui(controller: Controller) extends Frame with Observer:
  controller.add(this)
  title = "MILL"

  val millField: MillField = new MillField(controller)
  val showStones = new ShowStones(controller)
  val settingsDialog: Dialog = new SettingsDialog(controller)
  val topPanel = new BoxPanel(Orientation.Horizontal)
  val bottomPanel = new BoxPanel(Orientation.Horizontal)
  val gameStand: BoxPanel = new BoxPanel(Orientation.Horizontal)
  val gameStandLabel = new Label()
  gameStand.contents += gameStandLabel
  gameStandLabel.font = new Font(gameStandLabel.font.getName, 6, 20)
  val mainPanel = new BoxPanel(Orientation.Vertical):
    contents += gameStandLabel
    contents += createLabelBoxPanel("Stones to Set", "Delete Stones")
    contents += topPanel
    contents += millField
    contents += createLabelBoxPanel("Stones to Set", "Delete Stones")
    contents += bottomPanel

  contents = mainPanel
  menuBar = createMenuBar
  pack()
  visible = true
  redraw

  override def update(e: util.Event): Unit = e match {
    case Event.Quit => QuitConfirmationDialog(controller).showQuitConfirmationDialog
    case Event.Status => redraw
    case Event.Mill =>
      aktualise
      millField.ismill = true
  }

  private def aktualise: Unit =
    gameStand.contents.clear()
    gameStandLabel.text  =  s"${controller.gamefield.player.name}, delete a stone."
    gameStand.revalidate()
    gameStand.repaint()

  private def redraw: Unit =
    val playerPanels = updatePlayerStats
    Seq(topPanel.contents, bottomPanel.contents, gameStand.contents).foreach( _.clear())
    topPanel.contents ++= Seq(playerPanels._1, playerPanels._4)
    bottomPanel.contents ++= Seq(playerPanels._3, playerPanels._2)
    createNewFields
    gameStandLabel.text = controller.getGameStandLabelText()
    mainPanel.revalidate()
    mainPanel.repaint()
  
  def createLabelBoxPanel(label1: String, label2: String): BoxPanel =
    val panel = new BoxPanel(Orientation.Horizontal)
    panel.contents += new Label(label1)
    panel.contents += Swing.HStrut(300)
    panel.contents += new Label(label2)
    panel
  
  def createNewFields: Unit =
    millField.update(controller)
    millField.revalidate()
    millField.repaint()

  private def updatePlayerStats: (Panel, Panel, Panel, Panel) = 
    val playerStatistics = controller.PlayerStatics()
    (showStones.createImagePanel(playerStatistics._1, Color.white),
    showStones.createImagePanel(controller.gamesize - (playerStatistics._2 + playerStatistics._1), Color.white),
    showStones.createImagePanel(playerStatistics._3, Color.black),
    showStones.createImagePanel(controller.gamesize - (playerStatistics._4 + playerStatistics._3),Color.black))

  private def createMenuBar: MenuBar = 
    val settingsMenu: Menu = new Menu("Settings"):
      contents += new MenuItem(Action("Open Settings")(new SettingsDialog(controller).visible = true))
    val menuBar: MenuBar = new MenuBar:
      contents += settingsMenu
    menuBar 

  override def open(): Unit =
    super.open()
    listenTo(this)
    reactions += {
    case WindowClosing(_) => controller.quit("")
    }
