package de.htwg.se.Muehle
package controller

import model.Field
import model.Stone
import model.Player
import model.PlayerList
import model.StoneMovement

import util.Observable
import util.Event
import de.htwg.se.Muehle.model.StoneMovement

case class Controller(
    var field: Field,
    var activePlayer: Player,
    var playerlist: PlayerList
) extends Observable:
  def numberofstones(number: Int): Unit =
    playerlist = PlayerList(number)
    field = Field()
    activePlayer = playerlist.getFirstPlayer
    notifyObservers(Event.Set)
    notifyObservers(Event.Status)
  def quit: Unit = notifyObservers(Event.Quit)
  def put(to: Int, from: Int): Field =
    field == StoneMovement(activePlayer, field, to, from) match
      case false =>
        field = StoneMovement(activePlayer, field, to, from)
        val newPlayer = playerlist.getNextPlayer(activePlayer)
        if (activePlayer.stonetoput != 0)
          playerlist = playerlist.updateStonesInField(activePlayer)
        activePlayer = newPlayer
        notifyObservers(Event.Set)
        notifyObservers(Event.Status)
        field
      case true =>
        println("This move is not possible try again")
        field
  override def toString(): String = field.toString
  def inputText(): String =
    s"Give the number or the field where you want to set a stone, ${activePlayer.name} or q (quit)" + "\n" + playerlist
      .printStonesToSet()
