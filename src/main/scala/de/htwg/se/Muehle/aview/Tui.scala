package de.htwg.se.Muehle
package aview

import scala.io.StdIn.readLine
import controller.Controller
import util.Observer
import util.Event

val invalidInputMsg =
  "Invalid input. Please enter a valid number between 1 and 24, or 'q' to quit"
var loop = true

class Tui(controller: Controller) extends Observer:
  controller.add(this)
  def run =
    while (loop) {
      println(controller.toString())
      analyseInput(readLine)
    }
  override def update(e: Event): Unit =
    e match
      case Event.Quit   => loop = false
      case Event.Set    => println(controller.toString())
      case Event.Status => println(controller.printStonesToSet())
      case Event.IsMill =>
        println("Mühle")
        val input = readLine().toInt
        controller.isMill(input)

  def analyseInput(input: String): Boolean =
    input match
      case "q" =>
        println("GoodBye")
        controller.quit
        false
      case intValueString if controller.isValid(intValueString) =>
        controller.put(intValueString.toInt, readLine().toInt)
        true
      case "sG" =>
        controller.bildGameSet(3, false)
        true
      case "mG" =>
        controller.bildGameSet(6, false)
        true
      case "lG" =>
        controller.bildGameSet(9, false)
        true
      case "sSG" =>
        controller.bildGameSet(3, true)
        true
      case "mSG" =>
        controller.bildGameSet(6, true)
        true
      case "lSG" =>
        controller.bildGameSet(9, true)
        true

      case _: String =>
        println(invalidInputMsg)
        false
