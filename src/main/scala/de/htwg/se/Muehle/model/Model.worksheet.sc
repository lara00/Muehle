import de.htwg.se.Muehle.model.gameComponent.millimpl.Mill
import de.htwg.se.Muehle.controller.Controller
import de.htwg.se.Muehle.model.gameComponent.GameStap
import de.htwg.se.Muehle.model.Field
import de.htwg.se.Muehle.model.PlayerList
import de.htwg.se.Muehle.model.gameComponent.millimpl.Mill
import de.htwg.se.Muehle.model.HumanPlayer
import de.htwg.se.Muehle.model.Stone

val field: Field = Field()
val players: PlayerList = PlayerList(4)
val controller: Controller = Controller(
  GameStap(field, players.getFirstPlayer, players),
  HumanPlayer()
)
