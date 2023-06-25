package de.htwg.se.Muehle
package aview

import scala.io.StdIn.readLine
import util.{Observer, Event}
import scala.util.{Try, Success, Failure}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import controller.controllerComponent.IController
import com.google.inject.Inject
import org.slf4j.{Logger, LoggerFactory}

val invalidInputMsg =
  "Invalid input. Please enter a valid number between 1 and 24, or 'q' to quit"
var loop = true

class Tui(using var controller: IController) extends Observer:
  private val logger: Logger = LoggerFactory.getLogger(classOf[Tui])

  controller.add(this)

  def run(): Unit = 
    while (loop)
      logger.trace(controller.toString())
      analyseInput(readLineTry())

  override def update(e: Event): Unit = e match
    case Event.Quit   =>
      loop = false
    case Event.Status =>
      logger.trace(controller.printStonesToSet)
      logger.trace(controller.getGameStandLabelText)
      logger.trace(controller.toString())
    case Event.Mill   =>
      logger.trace("Mill delete a Stone")
      val futureInput = Future(readLine().toInt)
      futureInput.foreach(controller.mill)

  def readLineTry(): Try[String] = Try(readLine())

  def parseInputToIntOption(input: String): Try[Int] =
    Try(input.toInt)

  def analyseInput(input: Try[String]): Boolean = {
    input match {
      case Success("q") =>
        logger.trace("GoodBye")
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
        help()
        true
      case Success(intValueString) =>
        parseInputToIntOption(intValueString) match {
          case Success(intValue) if controller.isValid(intValueString) =>
            val input = readLineTry().flatMap(parseInputToIntOption)
            input.foreach(controller.put(intValue, _))
            true
          case _ =>
            logger.trace(invalidInputMsg)
            false
        }
      case Failure(_) => false
    }
  }
  def help(): Unit =
    val message =
      """Welcome to the game help!
        | 1          2         3
        |     4      5     6
        |         7  8  9
        | 10  11  12    13 14 15
        |         16 17 18
        |     19     20    21
        |22          23       24
        |
        |To make a move, follow these steps:
        |1. Enter the number of the field you want to move to (a number between 1 and 24).
        |2. Confirm the input.
        |3. Enter the number of the stone you want to move.
        |4. If you have fewer than 3 stones remaining, you can jump with your stones.
        |
        |Placing a New Stone:
        |To place a new stone on the board, enter -1 as the field number.
        |
        |Available game modes:
        |- lSG: Game with 3 stones for a single player.
        |- mSG: Game with 6 stones for a single player.
        |- lSG: Game with 9 stones for a single player.
        |- sG: Game with 3 stones for a player and a partner.
        |- mG: Game with 6 stones for a player and a partner.
        |- lG: Game with 9 stones for a player and a partner.
        |
        |Additional commands:
        |- z: Undo the last move.
        |- y: Redo a previously undone move.
        |- f: Save the current game state.
        |- l: Load a saved game state.
        |- q: Quit the game.
        |- h: Display the help text.
        |
        |Enjoy the game!
        |""".stripMargin
    logger.info(message)