package de.htwg.se.Muehle.model

import scala.util.Random

trait PlayerStrategy:
  final def makeMove(
      gameStap: GameStap,
      to: Int,
      from: Int
  ): (GameStap, MoveEvents) =
    val gamefield = handleMove(gameStap, to, from)
    handleResult(gameStap, gamefield)
  protected def handleMove(
      gameStap: GameStap,
      to: Int,
      from: Int
  ): (GameStap, MoveEvents)
  protected def handleResult(
      gameStap: GameStap,
      gamefield: (GameStap, MoveEvents)
  ): (GameStap, MoveEvents)

class HumanPlayer extends PlayerStrategy:
  protected def handleMove(
      gameStap: GameStap,
      to: Int,
      from: Int
  ): (GameStap, MoveEvents) =
    gameStap.timetoSetMoveJumporMill(to, from)
  protected def handleResult(
      gameStap: GameStap,
      gamefield: (GameStap, MoveEvents)
  ): (GameStap, MoveEvents) =
    gamefield

class AIPlayer(aiStones: List[Int] = Nil) extends PlayerStrategy {
  private var aimuhle = false
  var millgamestap =
    new GameStap(Field(), new Player(Stone.White, 0, 0), PlayerList(3))

  protected def handleMove(
      gameStap: GameStap,
      to: Int,
      from: Int
  ): (GameStap, MoveEvents) = {
    aimuhle match
      case false => {
        if (gameStap.player.name != Stone.Black) {
          val gamefield = gameStap.timetoSetMoveJumporMill(to, from)
          gamefield(1) match
            case MoveEvents.SetStone_Mill | MoveEvents.MoveStone_Mill =>
              gamefield
            case _ => {
              gamefield(0) match {
                case `gameStap` => (gameStap, gamefield(1))
                case _ => makeautomove(gamefield(0))
              }
            }
        } else {
          makeautomove(gameStap)
        }
      }
      case true => {
        var deletestone = millgamestap.handleMill(generateRandomNumber)
        while (deletestone(1) != MillEvents.DeleteStone) {
          println(generateRandomNumber)
          deletestone = millgamestap.handleMill(generateRandomNumber)
        }
        aimuhle = false
        (deletestone(0), MoveEvents.SetStone)
      }
  }

  def makeautomove(gameStap: GameStap): (GameStap, MoveEvents) = {
    gameStap match
            case _ if gameStap.player.stonetoput != 0 =>
              handleStonetoput(gameStap, -1)
            case _ if gameStap.player.stoneintheField > 3 =>
              handleStoneintheField(gameStap)
            case _ =>
              handleDefaultMove(gameStap)
  }

  protected def handleResult(
      gameStap: GameStap,
      gamefield: (GameStap, MoveEvents)
  ): (GameStap, MoveEvents) =
    gamefield

  private def handleStonetoput(
      gamefield: GameStap,
      to: Int
  ): (GameStap, MoveEvents) = {
    val randomNumber = field_is_Free(gamefield.field, gamefield.player.name)
    millcheck(randomNumber,-1, gamefield)
  }

  private def handleDefaultMove(gamefield: GameStap): (GameStap, MoveEvents) = {
    val randomNumber = field_is_Free(gamefield.field, gamefield.player.name)
    val randomElement = findValidRandomMove(gamefield)
    millcheck(randomNumber,randomElement, gamefield)
  }
  private def handleStoneintheField(
      gamefield: GameStap
  ): (GameStap, MoveEvents) = {
    val move = generate_move(gamefield)
    val newFrom = move(0)
    val newTo = move(1)
    millcheck(newTo,newFrom, gamefield)
  }

  private def findValidRandomMove(gamefield: GameStap): Int = {
    val random = new Random()
    var validMoveFound = false
    var randomElement = 0
    var playerStoneski = gamefield.field.getBlackStonePositions

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
      from = findValidRandomMove(gamefield)
    }
    resultOption
  }
  def generateRandomNumber: Int = {
    val random = new Random()
    random.nextInt(24) + 1
  }
  def millcheck(to:Int, from: Int, gamefield: GameStap) =  {
    val mill = gamefield.timetoSetMoveJumporMill(to, from)
    mill(1) match
      case MoveEvents.SetStone_Mill | MoveEvents.MoveStone_Mill =>
        aimuhle = true
        millgamestap = mill(0)
        gamefield.timetoSetMoveJumporMill(1, -1)
      case _ => mill
  }
}
