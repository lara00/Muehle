package de.htwg.se.Muehle
package controller

import java.io.StringReader

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model.{Field, Stone, PlayerList, Player, GameStap, AIPlayer, HumanPlayer}
import util.{Observable, Event, Observer}

class ControllerSpec extends AnyWordSpec with Matchers {
  val field = Field()
  val players = PlayerList(7)
  val controller =
    Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
  "A Controller" when {
    "creating input text" should {
      "return the correct output" in {
        val expectedOutput =
          s"Give the number or the field where you want to set a stone, ${controller.gamefield.player.name} or q (quit)" + "\n" + controller.gamefield.playerlist
            .printStonesToSet()
      }
    }
    "converting to a string" should {
      "return the correct representation of the field" in {
        val field: Field = Field()
        val players: PlayerList =
          PlayerList(7)
        val controller: Controller = Controller(
          GameStap(field, players.getFirstPlayer, players),
          AIPlayer()
        )
        controller.toString should be(
          field.toString
        )
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
    "simulate set complete game with 4 stones" in {
      val field: Field = Field()
      val players: PlayerList = PlayerList(4)
      val controller: Controller = Controller(
        GameStap(field, players.getFirstPlayer, players),
        HumanPlayer()
      )
      controller.redo
      val newField: Field = controller.retrunfield(1, -1)
      controller.noStep
      controller.gamefield.field should be(newField)
      controller.undo
      controller.gamefield.field should be(Field())
      controller.redo
      controller.gamefield.field should be(
        newField
      )
      val wrongvalueField: Field = controller.retrunfield(1, -1)
      val round2: Field = controller.retrunfield(2, -1)
      val round3: Field = controller.retrunfield(10, -1)
      val round4: Field = controller.retrunfield(3, -1)
      val input = new java.io.StringReader("1\n2\n")
      var r = controller.gamefield.field
      Console.withIn(input) {
        r = controller.retrunfield(22, -1)
      }
      newField should be(wrongvalueField)
      controller.undo
      controller.gamefield.field should be(round4)
      controller.redo
      controller.gamefield.field should be(r)

      val round5 = controller.gamefield.field
      newField should be(
        field.setStone(1, Stone.White)
      )

      val expectedRound5 = field
        .setStone(1, Stone.White)
        .setStone(10, Stone.White)
        .setStone(22, Stone.White)
        .setStone(3, Stone.Black)
      round5 should be(expectedRound5)
      val round6: Field = controller.retrunfield(6, -1)
      val round7: Field = controller.retrunfield(7, -1)
      val round8: Field = controller.retrunfield(8, -1)
      round8 should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(3, Stone.Black)
      )
      val round9 = controller.retrunfield(23, 22)
      round9 should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(23, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(3, Stone.Black)
      )
      controller.undo
      controller.gamefield.field should be(round8)
      controller.redo
      controller.gamefield.field should be(round9)
      val round10 = controller.retrunfield(24, 3)
      round10 should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(23, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
          .setStone(24, Stone.Black)
      )
      val input2 = new java.io.StringReader("24\n")
      Console.withIn(input2) {
        controller.retrunfield(22, 23)
      }

      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
      )
      controller.undo
      controller.gamefield.field should be(round10)
      controller.redo
      controller.gamefield.field should be(
        field
          .setStone(1, Stone.White)
          .setStone(10, Stone.White)
          .setStone(22, Stone.White)
          .setStone(7, Stone.White)
          .setStone(6, Stone.Black)
          .setStone(8, Stone.Black)
      )
    }
    "simulate set complett game with 4 stones as Singelplayer" in {
      val field: Field = Field()
      val players: PlayerList = PlayerList(4)
      val controller: Controller = Controller(
        GameStap(field, players.getFirstPlayer, players),
        AIPlayer()
      )
      val newField: Field = controller.retrunfield(1, -1)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 3, 1), Player(Stone.Black, 3, 1)))
      )
      val simulatefield = Field()
        .setStone(1, Stone.White)
        .setStone(15, Stone.White)
        .setStone(20, Stone.White)
        .setStone(19, Stone.Black)
        .setStone(22, Stone.Black)
        .setStone(20, Stone.Black)
      val playerStoneski: List[Int] = List(19, 20, 22)
      val r = AIPlayer(playerStoneski)
      controller.playerstrategy = r

      controller.gamefield = GameStap(
        simulatefield,
        Player(Stone.White, 0, 3),
        PlayerList(List(Player(Stone.White, 0, 3), Player(Stone.Black, 0, 3)))
      )
      val jumpKI: Field = controller.retrunfield(10, 1)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 0, 3), Player(Stone.Black, 0, 3)))
      )
      val simulatefieldtomove = Field()
        .setStone(1, Stone.White)
        .setStone(15, Stone.White)
        .setStone(20, Stone.White)
        .setStone(5, Stone.White)
        .setStone(19, Stone.Black)
        .setStone(22, Stone.Black)
        .setStone(20, Stone.Black)
        .setStone(3, Stone.Black)
      val playerStoneskitomove: List[Int] = List(19, 20, 22, 5)
      val playerstrategymove = AIPlayer(playerStoneskitomove)
      controller.playerstrategy = playerstrategymove
      controller.gamefield = GameStap(
        simulatefieldtomove,
        Player(Stone.White, 0, 4),
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
      )
      controller.retrunfield(8, 5)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
      )
    }
    "case the put is not possible" in {
      val field = Field().setStone(1, Stone.White)
      val players = PlayerList(7)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      val newField = controller.retrunfield(1, -1)
      newField should be(field.setStone(1, Stone.White))
    }
  }
}
