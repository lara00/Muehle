package de.htwg.se.Muehle
package controller

import model.{Field, Stone, PlayerList, Player, GameStap, AIPlayer}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import util.Observable
import util.Event
import util.Observer
import de.htwg.se.Muehle.model.HumanPlayer
import java.io.StringReader

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
    "simulate set complett game with 4 stones" in {
      val field: Field = Field()
      val players: PlayerList = PlayerList(4)
      val controller: Controller = Controller(
        GameStap(field, players.getFirstPlayer, players),
        HumanPlayer()
      )
      val newField: Field = controller.put(1, -1)
      val wrongvalueField: Field = controller.put(1, -1)
      val round2: Field = controller.put(2, -1)
      val round3: Field = controller.put(10, -1)
      val round4: Field = controller.put(3, -1)
      var round5 = controller.put(22, -1)
      newField should be(wrongvalueField)
      controller.isMill(4)
      controller.isMill(7)
      controller.isMill(2)
      round5 = controller.gamefield.field
      newField should be(
        field.setStone(1, Stone.White)
      )

      val expectedRound5 = field
        .setStone(1, Stone.White)
        .setStone(10, Stone.White)
        .setStone(22, Stone.White)
        .setStone(3, Stone.Black)
      round5 should be(expectedRound5)
      val round6: Field = controller.put(6, -1)
      val round7: Field = controller.put(7, -1)
      val round8: Field = controller.put(8, -1)
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
      val round9 = controller.put(23, 22)
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
      val round10 = controller.put(24, 3)
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
      val round11 = controller.put(22, 23)
      controller.isMill(7)
      controller.gamefield.field should be(round11)
    }
    "simulate set complett game with 4 stones as Singelplayer" in {
      val field: Field = Field()
      val players: PlayerList = PlayerList(4)
      val controller: Controller = Controller(
        GameStap(field, players.getFirstPlayer, players),
        AIPlayer()
      )
      val newField: Field = controller.put(1, -1)
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
      val jumpKI: Field = controller.put(10, 1)
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
      controller.put(8, 5)
      controller.gamefield.playerlist should be(
        PlayerList(List(Player(Stone.White, 0, 4), Player(Stone.Black, 0, 4)))
      )
    }
    "case the put is not possible" in {
      val field = Field().setStone(1, Stone.White)
      val players = PlayerList(7)
      val controller =
        Controller(GameStap(field, players.getFirstPlayer, players), AIPlayer())
      val newField = controller.put(1, -1)
      newField should be(field.setStone(1, Stone.White))
    }
  }
}
