import de.htwg.se.Muehle.controller.Controller
import de.htwg.se.Muehle.model.GameStap
import de.htwg.se.Muehle.model.Field
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.Mill
import de.htwg.se.Muehle.model.HumanPlayer
import de.htwg.se.Muehle.model.Stone

val field: Field = Field()
val players: PlayerList = PlayerList(4)
val controller: Controller = Controller(
  GameStap(field, players.getFirstPlayer, players),
  HumanPlayer()
)
