package de.htwg.se.Muehle.aview.gui

import scala.swing._
import scala.swing.event._
import java.awt.Color

class RoundButton(text: String) extends Button(text) {
  private val defaultBackgroundColor = Color.lightGray
  private val defaultRadius = 25
  background = defaultBackgroundColor

  override def paintComponent(g: Graphics2D): Unit = 
    val width = size.width - 1
    val height = size.height - 1
    val centerX = width / 2
    val centerY = height / 2
    val radius = defaultRadius
    g.setColor(background)
    g.fillOval(0, 0, width, height)
    background match 
      case Color.white => drawGradientCircles(g, centerX, centerY, radius, new Color(192, 192, 192))
      case Color.black => drawGradientCircles(g, centerX, centerY, radius, Color.lightGray)
      case _ =>
    super.paintComponent(g)

  override def paintBorder(g: Graphics2D): Unit = 
    g.setColor(foreground)
    g.drawOval(0, 0, size.width - 1, size.height - 1)

  def drawGradientCircles(g: Graphics2D, centerX: Int,centerY: Int, radius: Int, baseColor: Color) : Unit =
    val numCircles = 3
    val colorStep = 35
    for (i <- 1 to numCircles)
      val circleRadius = radius * i / numCircles
      val x = centerX - circleRadius
      val y = centerY - circleRadius
      val color = new Color(
        baseColor.getRed,baseColor.getGreen,
        baseColor.getBlue,baseColor.getAlpha - i * colorStep)
      g.setColor(color)
      g.fillOval(x, y, circleRadius * 2, circleRadius * 2)
      
  borderPainted = false
  focusPainted = false
  contentAreaFilled = false
}