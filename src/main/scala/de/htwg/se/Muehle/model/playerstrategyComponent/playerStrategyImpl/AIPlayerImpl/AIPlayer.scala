package de.htwg.se.Muehle.model
package playerstrategyComponent.playerStrategyImpl.AIPlayerImpl

import scala.util.Random
import de.htwg.se.Muehle.model.{MoveEvents, Stone}
import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.gameComponent.IGameStap
import de.htwg.se.Muehle.model.playerstrategyComponent.IPlayerStrategy
import de.htwg.se.Muehle.Default.given

class AIPlayer() extends IPlayerStrategy:
  var aimuhle = false
  var millgamestap: IGameStap = given_IGameStap

  protected def handleMove(gameStap: IGameStap, to: Int, from: Int): (IGameStap, MoveEvents) =
    aimuhle match
      case false => automove_withoutmill(gameStap,to,from)
      case true => automove_withmill

  def automove_withmill = 
    val finalDeletestone = LazyList.continually(millgamestap.handleMill(generateRandomNumber))
    .dropWhile(deletestone => deletestone(1) != MillEvents.DeleteStone).head
    aimuhle = false
    (finalDeletestone(0), MoveEvents.SetStone)

  def automove_withoutmill(gameStap: IGameStap, to: Int, from: Int) =
    if (gameStap.playername != Stone.Black)
      val gamefield = gameStap.timetoSetMoveJumporMill(to, from)
      gamefield(1) match
        case MoveEvents.SetStone_Mill | MoveEvents.MoveStone_Mill => gamefield
        case _ => gamefield(0) match 
                case `gameStap` => (gameStap, gamefield(1))
                case _ => makeautomove(gamefield(0))
    else
      makeautomove(gameStap)

  def makeautomove(gameStap: IGameStap): (IGameStap, MoveEvents) =
    gameStap match
            case _ if gameStap.gplayer.pstonetoput != 0 =>
              handleStonetoput(gameStap, -1)
            case _ if gameStap.gplayer.pstoneinField > 3 =>
              handleStoneintheField(gameStap)
            case _ =>
              handleDefaultMove(gameStap)

  protected def handleResult(gameStap: IGameStap, gamefield: (IGameStap, MoveEvents)): (IGameStap, MoveEvents) = gamefield

  private def handleStonetoput(gamefield: IGameStap, to: Int): (IGameStap, MoveEvents) = millcheck(field_is_Free(gamefield.gfield, gamefield.playername),-1, gamefield)

  private def handleDefaultMove(gamefield: IGameStap): (IGameStap, MoveEvents) = millcheck(field_is_Free(gamefield.gfield, gamefield.playername),findValidRandomMove(gamefield), gamefield)
  
  private def handleStoneintheField(gamefield: IGameStap): (IGameStap, MoveEvents) =
    val (move0, move1) = generate_move(gamefield)
    millcheck(move1,move0, gamefield)

  private def findValidRandomMove(gamefield: IGameStap): Int =
    val playerStoneski = gamefield.gfield.getBlackStonePositions.to(LazyList)
    playerStoneski.filter(element => gamefield.gfield.stones_field(element) == gamefield.playername)
    .drop(scala.util.Random.nextInt(playerStoneski.count(element => gamefield.gfield.stones_field(element) == gamefield.playername))).head

  private def field_is_Free(field: IField, stone: Stone): Int = LazyList.continually(generateRandomNumber).find(number => field.setStone(number, stone) != field).getOrElse(0)

  private def generate_move(gamefield: IGameStap): (Int, Int) = 
    val sequence: List[List[Int]] = List(
      List(1, 2, 10), List(2, 1, 3, 5), List(3, 2, 15), List(4, 5, 11), List(5, 2, 4, 6, 8), List(6, 5, 14),
      List(7, 12, 8), List(8, 7, 5, 9), List(9, 8, 13), List(10, 1, 11, 22), List(11, 10, 4, 12, 19), List(12, 11, 7, 16),
      List(13, 9, 18, 14), List(14, 6, 13, 15, 21), List(15, 14, 3, 24), List(16, 12, 17), List(17, 20, 18), List(18, 17, 13),
      List(19, 11, 20), List(20, 19, 17, 21, 23), List(21, 20, 14), List(22, 23, 24), List(23, 22, 20, 24), List(24, 23, 15))
    val validMoves = gamefield.gfield.getBlackStonePositions.filter(position => sequence(position - 1).tail.exists(element => gamefield.gfield.stones_field(element) == Stone.Empty))
    val move = Random.shuffle(validMoves).headOption.flatMap { from =>
    val toOptions = sequence(from - 1).tail.filter(element => gamefield.gfield.stones_field(element) == Stone.Empty)
    toOptions.headOption.map(randomTo => (from, randomTo))
    }.getOrElse((0, 0))
    move

  def generateRandomNumber: Int = scala.util.Random.nextInt(24) + 1

  def millcheck(to:Int, from: Int, gamefield: IGameStap) =
    val mill = gamefield.timetoSetMoveJumporMill(to, from)
    mill(1) match
      case MoveEvents.SetStone_Mill | MoveEvents.MoveStone_Mill =>
        aimuhle = true
        millgamestap = mill(0)
        gamefield.timetoSetMoveJumporMill(1, -1)
      case _ => mill