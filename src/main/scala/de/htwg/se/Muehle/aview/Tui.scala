package de.htwg.se.Muehle
package aview

import scala.io.StdIn.readLine
import controller.Controller
import util.Observer
import util.Event

val invalidInputMsg =
  "Invalid input. Please enter a valid number between 1 and 24, or 'q' to quit"

class Tui(controller: Controller) extends Observer:
  controller.add(this)
  def run =
    println(controller.field.toString())
    analyseInput(readLine)
  override def update(e: Event): Unit =
    e match
      case Event.Quit   => sys.exit(0)
      case Event.Set    => println(controller.field.toString())
      case Event.Status => println(controller.playerlist.printStonesToSet())

  def analyseInput(input: String): Unit =
    input match
      case "q" => controller.quit
      case intValueString if controller.field.isFieldValid(intValueString) =>
        controller.activePlayer.statusplayer() match
          case 0 => controller.put(intValueString.toInt)
          case 1 => println("Not implementet"); sys.exit(0)
          case 2 => println("Not implementet"); sys.exit(0)
      case stonestoput if stonestoput.matches("n[37]") =>
        controller.numberofstones(stonestoput.substring(1).toInt)
      case _: String => println(invalidInputMsg)
    analyseInput(readLine)
