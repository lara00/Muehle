package de.htwg.se.Muehle
package aview
package gui

import scala.swing._
import scala.swing.event._
import java.awt.{Color}
import de.htwg.se.Muehle.controller.Controller

class ShowStones(controller : Controller) extends Panel:
  def createImagePanel(value: Int, color: Color): Panel =
    new Panel:
      val stonesCount: Int = value

      override def paintComponent(g: Graphics2D): Unit = 
        super.paintComponent(g)
        val imageSize = new Dimension(40, 40)
        val circleSize = 40
        val circleGap = 3
        val totalWidth = stonesCount * (circleSize + circleGap) - circleGap
        val startX = (size.width - totalWidth) / 2
        val startY = (size.height - circleSize) / 2

        for (i <- 0 until stonesCount)
          val circleX = startX + i * (circleSize + circleGap)
          val circleY = startY
          g.setColor(color)
          g.fillOval(circleX, circleY, circleSize, circleSize)

      preferredSize = new Dimension(value * (40), 80)
      background = controller.iswhite(color)
      listenTo(mouse.clicks)

      reactions += { case e: MouseClicked =>
        repaint()
      }
