import de.htwg.se.Muehle.model.MillHandler
import de.htwg.se.Muehle.controller.Controller
import de.htwg.se.Muehle.model.GameStap
import de.htwg.se.Muehle.model.Field
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.Mill

import de.htwg.se.Muehle.model.{AIPlayer, HumanPlayer}
import scala.util.{Try}
val minField = 1
val maxField = 24

val field: Field = Field()
val players: PlayerList = PlayerList(4)
val controller: Controller = Controller(
  GameStap(field, players.getFirstPlayer, players),
  HumanPlayer()
)
val newField: Field = controller.retrunfield(1, -1)
controller.gamefield
controller.undo
controller.gamefield
controller.redo
controller.gamefield

val wrongvalueField: Field = controller.retrunfield(1, -1)
val round2: Field = controller.retrunfield(2, -1)
val round3: Field = controller.retrunfield(10, -1)
val round4: Field = controller.retrunfield(3, -1)
val input = new java.io.StringReader("2\n")
Console.withIn(input) {
  controller.retrunfield(22, -1)
}
val round5 = controller.gamefield.field

val round6: Field = controller.retrunfield(6, -1)
val round7: Field = controller.retrunfield(7, -1)
val round8: Field = controller.retrunfield(8, -1)
val round9 = controller.retrunfield(23, 22)
controller.gamefield
controller.undo
controller.gamefield
controller.redo
controller.gamefield
val round10 = controller.retrunfield(24, 3)
val input2 = new java.io.StringReader("24\n")
Console.withIn(input2) {
  controller.retrunfield(22, 23)
}
controller.gamefield
controller.undo
controller.gamefield
controller.redo
controller.gamefield
