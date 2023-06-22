package de.htwg.se.Muehle
package aview

import scala.io.StdIn.readLine
import util.{Observer, Event}
import scala.util.{Try, Success, Failure}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import controller.controllerComponent.IController
import com.google.inject.Inject

val invalidInputMsg =
  "Invalid input. Please enter a valid number between 1 and 24, or 'q' to quit"
var loop = true
class Tui(using var controller: IController) extends Observer:
  controller.add(this)

  def run(): Unit = 
    while (loop) 
      println(controller.toString())
      analyseInput(readLineTry())

  override def update(e: Event): Unit = e match 
    case Event.Quit   => loop = false
    case Event.Status => 
      println(controller.printStonesToSet)
      println(controller.getGameStandLabelText)
      println(controller.toString())
    case Event.Mill =>
      println("Mill delete a Stone")
      val futureInput = Future(readLine().toInt)
      futureInput.foreach(controller.mill)

  def readLineTry(): Try[String] = Try(readLine())

  def parseInputToIntOption(input: String): Try[Int] =
    Try(input.toInt)

  def analyseInput(input: Try[String]): Boolean = 
    input match
      case Success("q") =>
        println("GoodBye")
        controller.quit("")
        false
      case Success("z") =>
        controller.undo
        true
      case Success("y") =>
        controller.redo
        true
      case Success("f") =>
        controller.save
        true
      case Success("l") =>
        controller.load
        true
      case Success("sG") =>
        controller.bildGameSet(3, false)
        true
      case Success("mG") =>
        controller.bildGameSet(6, false)
        true
      case Success("lG") =>
        controller.bildGameSet(9, false)
        true
      case Success("sSG") =>
        controller.bildGameSet(3, true)
        true
      case Success("mSG") =>
        controller.bildGameSet(6, true)
        true
      case Success("lSG") =>
        controller.bildGameSet(9, true)
        true
      case Success("h") =>
        help
        true 
      case Success(intValueString) =>
        parseInputToIntOption(intValueString) match
          case Success(intValue) if controller.isValid(intValueString) =>
            val input = readLineTry().flatMap(parseInputToIntOption)
            input.foreach(controller.put(intValue, _))
            true
          case _ =>
            println(invalidInputMsg)
            false
      case Failure(_) => false

def help: String = 
  val message =
    """First, enter the number of the field you want to move to (a number between 1 and 24),
      |confirm the input, and then enter the number of the stone you want to move.
      |To place a stone, enter -1.
      |
      |lSG = Game with 3 stones alone
      |mSG = Game with 6 stones alone
      |lSG = Game with 9 stones alone
      |sG = Game with 3 stones and a partner
      |mG = Game with 6 stones and a partner
      |lG = Game with 9 stones and a partner
      |
      |z = Undo
      |y = Redo
      |f = Save
      |l = Load
      |q = Quit
      |h = help text
      |""".stripMargin
  message



