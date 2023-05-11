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
    while (true) {
      println(controller.field.toString())
      analyseInput(readLine)
    }
  override def update(e: Event): Unit =
    e match
      case Event.Quit   => sys.exit(0)
      case Event.Set    => println(controller.field.toString())
      case Event.Status => println(controller.playerlist.printStonesToSet())

  def analyseInput(input: String): Boolean =
    input match
      case "q" =>
        controller.quit
        true
      case intValueString if controller.field.isFieldValid(intValueString) =>
        controller.put(intValueString.toInt, readLine().toInt)
        true
      case stonestoput if stonestoput.matches("n[37]") =>
        controller.numberofstones(stonestoput.substring(1).toInt)
        true
      case _: String =>
        println(invalidInputMsg)
        false
