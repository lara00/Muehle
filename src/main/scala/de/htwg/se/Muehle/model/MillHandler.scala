package de.htwg.se.Muehle.model
case class MillHandler(aktivGame: GameStap) {
  def funktion(delete: Int, gamestrategy: PlayerStrategy): GameStap =
    aktivGame match {
      case GameStap(field, player, playerlist)
          if player.name != field.fields(delete)
            && !Mill(field, VerticalAndHorizontalMillChecker).isMill(delete)
            && (playerlist.allowedtodeleteastone(player)) =>
        updateGameStapAfterDeleteStone(aktivGame, delete)

      case GameStap(_, player, playerlist)
          if playerlist.threeStonesontheField(player) =>
        println("${player.name} winns")
        updateGameStapAfterDeleteStone(aktivGame, delete)
      case _ =>
        println("This stone is not possible to delete")
        if (
          Mill(aktivGame.field, VerticalAndHorizontalMillChecker).isMill(delete)
        ) {
          println(
            "This stone is not possible to delete, because is part of a Mill"
          )
        }
        aktivGame
    }

  private def updateGameStapAfterDeleteStone(
      gameStap: GameStap,
      delete: Int
  ): GameStap = {
    val newPlayer = gameStap.playerlist.getNextPlayer(gameStap.player)
    val newfield = gameStap.field.deleteStone(delete, newPlayer.name)
    val newPlayerlist = gameStap.playerlist.updateStonesafterMill(newPlayer)
    GameStap(newfield, newPlayer.decrementStoneintheField, newPlayerlist)
  }
}
