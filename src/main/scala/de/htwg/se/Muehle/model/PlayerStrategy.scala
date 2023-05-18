package de.htwg.se.Muehle.model

import scala.util.Random
import de.htwg.se.Muehle.model.Field
import de.htwg.se.Muehle.controller.Controller

/*Template Pattern*/
trait PlayerStrategy {
  final def makeMove(gameStap: GameStap, to: Int, from: Int): GameStap = {
    val gamefield = handleMove(gameStap, to, from)
    handleResult(gameStap, gamefield)
  }
  protected def handleMove(gameStap: GameStap, to: Int, from: Int): GameStap
  protected def handleResult(gameStap: GameStap, gamefield: GameStap): GameStap
}

class HumanPlayer extends PlayerStrategy {
  protected def handleMove(gameStap: GameStap, to: Int, from: Int): GameStap = {
    Gamemove.chainOfResponsibility(gameStap, to, from)
  }
  protected def handleResult(
      gameStap: GameStap,
      gamefield: GameStap
  ): GameStap = {
    gamefield
  }
}

class AIPlayer(aiStones: List[Int] = Nil) extends PlayerStrategy {
  private var aimuhle = false
  var playerStoneski: List[Int] = aiStones
  private val playerlist: List[Int] = Nil

  protected def handleMove(gameStap: GameStap, to: Int, from: Int): GameStap = {
    val gamefield = Gamemove.chainOfResponsibility(gameStap, to, from)
    gamefield match {
      case `gameStap` => gameStap
      // Mill not implemented
      /*
      case _
          if gamefield.player.name == gameStap.player.name && gamefield.field != gameStap.field =>
        playerStoneski = to :: playerStoneski
        gamefield
       */
      case _ if gamefield.player.stonetoput != 0 =>
        handleStonetoput(gamefield, to)
      case _ if gamefield.player.stoneintheField > 3 =>
        handleStoneintheField(gamefield)
      case _ =>
        handleDefaultMove(gamefield)
    }
  }

  protected def handleResult(
      gameStap: GameStap,
      gamefield: GameStap
  ): GameStap = {
    gamefield
  }

  private def handleStonetoput(gamefield: GameStap, to: Int): GameStap = {
    val randomNumber = field_is_Free(gamefield.field, gamefield.player.name)
    playerStoneski = to :: playerStoneski
    playerStoneski = randomNumber :: playerStoneski
    Gamemove.chainOfResponsibility(gamefield, randomNumber, -1)
  }

  private def handleStoneintheField(gamefield: GameStap): GameStap = {
    val (newFrom, newTo) = generate_move(gamefield)
    playerStoneski = newTo :: playerStoneski
    playerStoneski = playerStoneski.filterNot(_ == newFrom)
    Gamemove.chainOfResponsibility(gamefield, newTo, newFrom)
  }

  private def handleDefaultMove(gamefield: GameStap): GameStap = {
    val randomNumber = field_is_Free(gamefield.field, gamefield.player.name)
    val randomElement = findValidRandomMove(gamefield)
    playerStoneski = randomNumber :: playerStoneski
    playerStoneski = playerStoneski.filterNot(_ == randomElement)
    Gamemove.chainOfResponsibility(gamefield, randomNumber, randomElement)
  }

  private def findValidRandomMove(gamefield: GameStap): Int = {
    val random = new Random()
    var validMoveFound = false
    var randomElement = 0

    while (!validMoveFound) {
      randomElement = playerStoneski(random.nextInt(playerStoneski.length))
      if (gamefield.field.fields(randomElement) == gamefield.player.name)
        validMoveFound = true
      else
        playerStoneski = playerStoneski.filterNot(_ == randomElement)
    }
    randomElement
  }

  private def field_is_Free(field: Field, stone: Stone): Int = {
    val random = new Random()
    var randomNumber = 0
    var loop = true

    while (loop) {
      randomNumber = random.nextInt(24) + 1
      if (field.setStone(randomNumber, stone) != field)
        loop = false
    }
    randomNumber
  }

  private def generate_move(gamefield: GameStap): (Int, Int) = {
    var loop = true
    val sequence: List[List[Int]] = List(
      List(1, 2, 10),
      List(2, 1, 3, 5),
      List(3, 2, 15),
      List(4, 5, 11),
      List(5, 2, 4, 6, 8),
      List(6, 5, 14),
      List(7, 12, 8),
      List(8, 7, 5, 9),
      List(9, 8, 13),
      List(10, 1, 11, 22),
      List(11, 10, 4, 12, 19),
      List(12, 11, 7, 16),
      List(13, 9, 18, 14),
      List(14, 6, 13, 15, 21),
      List(15, 14, 3, 24),
      List(16, 12, 17),
      List(17, 20, 18),
      List(18, 17, 13),
      List(19, 11, 20),
      List(20, 19, 17, 21, 23),
      List(21, 20, 14),
      List(22, 23, 24),
      List(23, 22, 20, 24),
      List(24, 23, 15)
    )
    var resultOption = (0, 0)
    var from = findValidRandomMove(gamefield)
    while (loop) {
      val listto = sequence(from - 1)
      val shuffledElements = Random.shuffle(listto.tail)
      shuffledElements.foreach { element =>
        if (gamefield.field.fields(element) == Stone.Empty) {
          resultOption = (from, element)
          loop = false
        }
      }
      from = findValidRandomMove(gamefield) // Aktualisierung von 'from'
    }
    resultOption
  }
}
