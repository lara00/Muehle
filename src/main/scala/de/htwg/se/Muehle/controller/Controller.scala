package de.htwg.se.Muehle
package controller

import model.Field
import model.Stone
import model.Player
import model.PlayerList
import model.SetStone

import util.Observable
import util.Event

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
  def put(number: Int): Field =
    field == field.setStone(number, activePlayer.name) match
      case false =>
        field = field.setStone(number, activePlayer.name)
        val newPlayer = playerlist.getNextPlayer(activePlayer)
        playerlist = playerlist.updateStonesInField(activePlayer)
        activePlayer = newPlayer
        notifyObservers(Event.Set)
        notifyObservers(Event.Status)
        field
      case true =>
        println("The field is not EMPTY, try again")
        field
  override def toString(): String = field.toString
  def inputText(): String =
    s"Give the number or the field where you want to set a stone, ${activePlayer.name} or q (quit)" + "\n" + playerlist
      .printStonesToSet()
