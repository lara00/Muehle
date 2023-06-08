package de.htwg.se.Muehle
package controller

import model.{
  Stone,
  Field,
  PlayerList,
  GameStap,
  MillEvents,
  PlayerStrategy,
  GamefieldBuilder,
  Mill,
  MoveEvents
}
import util.{Observable, Event, UndoManager}
import de.htwg.se.Muehle.model.AIPlayer
import java.awt.Color

case class Controller(
    var gamefield: GameStap,
    var playerstrategy: PlayerStrategy
) extends Observable:
  var gamesize = 0;
  val undoManager = new UndoManager[GameStap]

  def undo: Unit =
    gamefield = undoManager.undoStep(gamefield)

  def redo: Unit =
    gamefield = undoManager.redoStep(gamefield)

  def bildGameSet(number: Int, singlegamer: Boolean): Unit =
    gamesize = number
    val game = new GamefieldBuilder()
      .addStonesToPut(number)
      .addSingleGamer(singlegamer)
      .build()
    gamefield = game.gamesetting
    playerstrategy = game.gamestrategy
    notifyObservers(Event.Status)

  def quit(readsonforquit: String): Unit = notifyObservers(Event.Quit)

  def mill(delete: Int) =
    notifyObservers(Event.Status)
    val mill = gamefield.handleMill(delete)
    PutCommand(Move(mill(1), mill(0), playerstrategy, delete, 0))
    mill(1) match
      case MillEvents.DeleteStone =>
        gamefield = mill(0)
        notifyObservers(Event.Status)
        if (playerstrategy.getClass() == new AIPlayer().getClass())
          gamefield = playerstrategy.makeMove(gamefield, 1, -1)(0)
          notifyObservers(Event.Status)
      case MillEvents.EndGame =>
        gamefield = mill(0)
        notifyObservers(Event.Quit)
      case MillEvents.WrongDelete =>
        notifyObservers(Event.Mill)

  def put(to: Int, from: Int): Unit =
    val move = playerstrategy.makeMove(gamefield, to, from)
    undoManager.doStep(
      gamefield,
      PutCommand(Move(move(1), gamefield, playerstrategy, to, from))
    )
    move(1) match {
      case MoveEvents.SetStone_Mill | MoveEvents.MoveStone_Mill =>
        gamefield = move(0)
        notifyObservers(Event.Status)
        notifyObservers(Event.Mill)
      case _ =>
        gamefield = move(0)
        notifyObservers(Event.Status)
    }
  override def toString(): String = gamefield.field.toString

  def isValid(a: String): Boolean = gamefield.field.isFieldValid(a)

  def printStonesToSet(): String = gamefield.playerlist.printStonesToSet()

  def PlayerStatics(): (Int, Int, Int, Int) =
    val player1 = gamefield.playerlist.getFirstPlayer
    val player2 = gamefield.playerlist.getNextPlayer(player1)
    (
      player1.stonetoput,
      player1.stoneintheField,
      player2.stonetoput,
      player2.stoneintheField
    )

  def setormove(): Boolean =
    gamefield.player.stonetoput != 0

  def getGameStandLabelText(): String =
    gamefield.player.stonetoput match
      case 0 =>
        if (gamefield.playerlist.threeStonesontheField(gamefield.getNextPlayer))
          s"${gamefield.player.name}, jump with a stone."
        else
          s"${gamefield.player.name}, click on the field where you want to place the stone, and then click on the stone you want to move."
      case _ => s"${gamefield.player.name}, press on a field to place a stone."

  def getGameState(value: Int): Int =
    gamefield.field.fields(value) match
      case Stone.Empty => 1
      case Stone.White => 2
      case Stone.Black => 3

  def handleMillCase(index: Int): Boolean =
    gamefield.handleMill(index + 1)(1) match
      case MillEvents.DeleteStone | MillEvents.EndGame =>
        mill(index + 1)
        false
      case MillEvents.WrongDelete => true

  def iswhite(color: Color): Color =
    if (color == Color.WHITE) Color.BLACK else Color.WHITE
