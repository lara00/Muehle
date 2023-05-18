package de.htwg.se.Muehle.model

/*State Pattern ?*/
object StoneMovement {
  def apply(player: Player, field: Field, to: Int, from: Int): Field = {
    var movement = setstone(player, field, to)
    from match {
      case -1 => movement = setstone(player, field, to)
      case i if (1 to 24).contains(i) =>
        movement = MoveStone(player, field, to, i)
    }
    movement
  }
  def setstone(player: Player, field: Field, to: Int): Field = {
    println("Set a Stone")
    field.setStone(to, player.name)
  }
}
