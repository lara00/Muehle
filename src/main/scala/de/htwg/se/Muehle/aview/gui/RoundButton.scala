package de.htwg.se.Muehle.aview.gui

import scala.swing._
import scala.swing.event._
import java.awt.Color

class RoundButton(text: String) extends Button(text):
  private val defaultBackgroundColor = Color.lightGray
  private val defaultRadius = 25
  background = defaultBackgroundColor

  override def paintComponent(g: Graphics2D): Unit = 
    g.setColor(background)
    g.fillOval(0, 0, size.width - 1, size.height - 1)
    background match 
      case Color.white => drawGradientCircles(g, (size.width - 1) / 2, (size.height - 1) / 2, defaultRadius, new Color(192, 192, 192))
      case Color.black => drawGradientCircles(g, (size.width - 1) / 2, (size.height - 1) / 2, defaultRadius, Color.lightGray)
      case _ =>
    super.paintComponent(g)

  override def paintBorder(g: Graphics2D): Unit = 
    g.setColor(foreground)
    g.drawOval(0, 0, size.width - 1, size.height - 1)

  def drawGradientCircles(g: Graphics2D, centerX: Int,centerY: Int, radius: Int, baseColor: Color) : Unit =
    val numCircles = 3
    for (i <- 1 to numCircles)
      val circleRadius = radius * i / numCircles
      val color = new Color(
        baseColor.getRed,baseColor.getGreen,
        baseColor.getBlue,baseColor.getAlpha - i * 35)
      g.setColor(color)
      g.fillOval(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2)
      
  borderPainted = false
  focusPainted = false
  contentAreaFilled = false