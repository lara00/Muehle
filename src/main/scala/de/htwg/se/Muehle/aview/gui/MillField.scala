package de.htwg.se.Muehle
package aview
package gui

import scala.swing._
import scala.swing.event.ButtonClicked
import scala.collection.mutable
import java.awt.{Color}
import de.htwg.se.Muehle.controller.controllerComponent.IController

class MillField(controller: IController) extends GridBagPanel:
  var ismill = false
  private var clickedButtons = 0
  val buttonPosition_ = mutable.Map[Int, Button]()
  private var button1: (RoundButton, Int) = (null, 0)
  private val fieldSize = 800
  private val buttonSize = 75
  private val buttonPositions = List(
    (0, 0), (0, 3), (0, 6), (1, 1), (1, 3), (1, 5),
    (2, 2), (2, 3), (2, 4), (3, 0), (3, 1), (3, 2),
    (3, 4), (3, 5), (3, 6), (4, 2), (4, 3), (4, 4),
    (5, 1), (5, 3), (5, 5), (6, 0), (6, 3), (6, 6))

  background = new Color(220, 220, 220)
  preferredSize = new Dimension(fieldSize, fieldSize)

  for (((row, col), index) <- buttonPositions.zipWithIndex)
    val button = new RoundButton(s"${index + 1}")
    buttonPosition_ += (index + 1) -> (button)
    layout(button) = new Constraints:
      grid = (col, row)
      insets = new Insets(5, 5, 5, 5)
    button.preferredSize = new Dimension(buttonSize, buttonSize)
    button.reactions += { case ButtonClicked(_) =>
      if (ismill) ismill = controller.handleMillCase(index)
      else handleNonMillCase(index, button)
    }

  private def handleNonMillCase(index: Int, button: RoundButton): Unit =
    controller.setormove match
      case true => controller.put(index + 1, -1)
      case false =>
        clickedButtons match
          case 0 => button1 = (button, index + 1); clickedButtons += 1
          case 1 => clickedButtons = 0; controller.put(button1._2, index + 1)

  override protected def paintComponent(g: Graphics2D): Unit =
    super.paintComponent(g)
    g.setColor(Color.black)
    val buttonPosition = buttonPosition_
    val buttonPositionsToConnect = 
      List((1, 3), (1, 22), (2, 8),(3, 24), (4, 6), (4, 19), (6, 21),  (17,23),
      (7, 9), (7, 16), (9, 18), (10, 12), (13, 15),(16, 18), (19, 21), (22, 24))

    buttonPositionsToConnect.foreach { case (startIndex, endIndex) =>
    val (pos1, pos2) = (buttonPosition(startIndex).location, buttonPosition(endIndex).location)
    g.drawLine(pos1.x + buttonSize / 2, pos1.y + buttonSize / 2, pos2.x + buttonSize / 2, pos2.y + buttonSize / 2)}

  def update(controller: IController): Unit =
    for ((button, index) <- contents.collect { case b: RoundButton => b }.zipWithIndex)
      val gameState = controller.getGameState(index + 1)
      button.background = gameState match
        case 1 => Color.lightGray
        case 2 => Color.white
        case 3 => Color.black
