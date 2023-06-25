package de.htwg.se.Muehle
package aview
package gui

import scala.swing._
import scala.swing.event._
import java.awt.{Color}
import de.htwg.se.Muehle.controller.controllerComponent.IController

class ShowStones(controller : IController):
  def createBoxPanel(color: Color, stones: Int, delete: Int): BoxPanel = 
    val boxpanel = new BoxPanel(Orientation.Vertical):
      contents += new BoxPanel(Orientation.Horizontal):
        contents += new Label("Stones to Set")
        contents += Swing.HStrut(300)
        contents += new Label("Delete Stones")
      contents += new BoxPanel(Orientation.Horizontal):
        contents += createImagePanel(stones, color)
        contents += createImagePanel(delete, controller.iswhite(color))
    boxpanel

  def createImagePanel(stonesCount: Int, color: Color): Panel =
    new Panel:
      override def paintComponent(g: Graphics2D): Unit = 
        super.paintComponent(g)
        val circleSize = 40
        val totalWidth = (stonesCount * (circleSize + 3) - 3)
        for (i <- 0 until stonesCount)
          val circleX = ((size.width - totalWidth) / 2) + i * (circleSize + 3)
          val circleY = (size.height - circleSize) / 2
          g.setColor(color)
          g.fillOval(circleX, circleY, circleSize, circleSize)

      preferredSize = new Dimension(stonesCount * (40), 80)
      background = controller.iswhite(color)