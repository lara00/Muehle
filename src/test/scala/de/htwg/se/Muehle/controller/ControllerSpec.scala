package de.htwg.se.Muehle
package controller

import model.{Field, Stone, PlayerList, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import util.Observable
import util.Event
import util.Observer

class ControllerSpec extends AnyWordSpec with Matchers {
  val field = Field()
  val players = PlayerList(7)
  val controller = Controller(field, players.getFirstPlayer, players)
  "A Controller" when {
    "creating input text" should {
      "return the correct output" in {
        val expectedOutput =
          s"Give the number or the field where you want to set a stone, ${controller.activePlayer.name} or q (quit)\n${players.printStonesToSet()}"
        controller.inputText() should be(expectedOutput)
      }
    }
    "converting to a string" should {
      "return the correct representation of the field" in {
        val field = Field()
        val players = PlayerList(7)
        val controller = Controller(field, players.getFirstPlayer, players)
        controller.toString() should be(field.toString())
      }
    }
    "quit" should {
      "notify observers with the quit event" in {
        var event: Option[Event] = None
        controller.add(new Observer {
          override def update(e: Event): Unit = event = Some(e)
        })
        controller.quit
        event should contain(Event.Quit)
      }
    }
    "numberofstones" should {
      "notify observers with the numberq of stones event" in {
        var event: Option[Event] = None
        controller.add(new Observer {
          override def update(e: Event): Unit = event = Some(e)
        })
        controller.numberofstones(3)
        event should contain(Event.Status)
      }
    }
    "putting a stone on the field" should {
      "update the field, players, and notify observers with Set and Status events" in {
        val field = Field()
        val players = PlayerList(7)
        val controller = Controller(field, players.getFirstPlayer, players)
        val newField = controller.put(1, -1)
        newField should be(field.setStone(1, Stone.White))
      }
    }
    "case the put is not possible" in {
      val field = Field().setStone(1, Stone.White)
      val players = PlayerList(7)
      val controller = Controller(field, players.getFirstPlayer, players)
      val newField = controller.put(1, -1)
      newField should be(field.setStone(1, Stone.White))
    }
  }
}
