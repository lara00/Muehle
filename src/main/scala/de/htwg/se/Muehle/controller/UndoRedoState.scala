package de.htwg.se.Muehle
package controller

import model.{GameStap, Mill, MillList, PlayerList}

trait UndoRedoState {
  def movemanager(move: Move, mill: List[Int]): (GameStap, List[Int])
}

case class UndoRedoStepState(isRedo: Boolean, move: Move)
    extends UndoRedoState {
  val nextState: UndoRedoState = {
    val field = move.gamefield.field
    val player = move.gamefield.player
    val millCheckField = field.setStone(move.to, player.name)
    val isMill = Mill(millCheckField).isMill(move.to)

    (isRedo, isMill, move.from) match {
      case (true, true, _)    => MillState_redo
      case (true, false, -1)  => SetStoneMove
      case (true, false, _)   => Removestone_redo
      case (false, true, -1)  => MillStateSet_undo
      case (false, true, _)   => MillStatemove_undo
      case (false, false, -1) => DeleteSetStone_undo
      case (false, false, _)  => UndoStoneMove
    }
  }

  override def movemanager(move: Move, mill: List[Int]): (GameStap, List[Int]) =
    nextState.movemanager(move, mill)

  private object MillStateSet_undo extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) = {
      val nextPlayer = move.gamefield.playerlist.getNextPlayer(
        move.gamefield.player.incrementStoneintheField
      )
      val newField = move.gamefield.field
        .setStone(MillList.list.head, nextPlayer.name)
        .deleteStone(move.to, move.gamefield.player.name)
      val newlist = MillList.list.head :: mill
      MillList.deleteElement()
      val newPlayer = move.gamefield.player.decrementStoneintheField
      val newPlayerList = PlayerList(newPlayer, nextPlayer)
      (GameStap(newField, newPlayer, newPlayerList), newlist)
    }
  }
  private object MillStatemove_undo extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) = {
      val nextPlayer = move.gamefield.playerlist.getNextPlayer(
        move.gamefield.player
      )
      val newField = move.gamefield.field
        .setStone(MillList.list.head, nextPlayer.name)
        .deleteStone(move.to, move.gamefield.player.name)
      val newlist = MillList.list.head :: mill
      MillList.deleteElement()
      val newPlayer = move.gamefield.player.decrementStoneintheField
      val newPlayerList = PlayerList(newPlayer, nextPlayer)
      (GameStap(newField, newPlayer, newPlayerList), newlist)
    }
  }

  private object MillState_redo extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) = {
      val value = mill.head
      val input = new java.io.StringReader(value.toString() + "\n")
      var game = move.gamefield
      Console.withIn(input) {
        game = move.playerstrategy.makeMove(move.gamefield, move.to, move.from)
      }
      (game, mill.tail)
    }
  }

  private object SetStoneMove extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) =
      (move.playerstrategy.makeMove(move.gamefield, move.to, move.from), mill)
  }
  private object UndoStoneMove extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) =
      (
        GameStap(
          move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name)
            .setStone(move.from, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist
        ),
        mill
      )
  }
  private object DeleteSetStone_undo extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) =
      (
        GameStap(
          move.gamefield.field
            .deleteStone(move.to, move.gamefield.player.name),
          move.gamefield.player,
          move.gamefield.playerlist
        ),
        mill
      )
  }
  private object Removestone_redo extends UndoRedoState {
    override def movemanager(
        move: Move,
        mill: List[Int]
    ): (GameStap, List[Int]) =
      (
        GameStap(
          move.gamefield.field
            .deleteStone(move.from, move.gamefield.player.name)
            .setStone(move.to, move.gamefield.player.name),
          move.gamefield.getNextPlayer,
          move.gamefield.playerlist
        ),
        mill
      )
  }
}
