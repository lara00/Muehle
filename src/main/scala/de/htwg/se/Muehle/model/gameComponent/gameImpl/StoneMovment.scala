package de.htwg.se.Muehle.model.gameComponent.gameImpl

import de.htwg.se.Muehle.model.fieldComponent.IField
import de.htwg.se.Muehle.model.playerComponent.IPlayer

trait StoneMovement:
  def move(player: IPlayer, field: IField, to: Int, from: Int): IField

class Jump extends StoneMovement:
  override def move(player: IPlayer, field: IField, to: Int, from: Int): IField = field.movestone(from, to, player.pname)

class SetaStone extends StoneMovement:
  override def move(player: IPlayer, field: IField, to: Int, from: Int): IField = field.setStone(to, player.pname)

class Switch extends StoneMovement:
  override def move(player: IPlayer, field: IField, to: Int, from: Int): IField = if (switch(from, to)) field.movestone(from, to, player.pname) else field

  private def switch(from: Int, to: Int): Boolean = 
    val from_ = if (from > 12) 25 - from else from
    val to_ = if (from > 12) 25 - to else to
    val canSwitch = from_ match
      case 10 => to_ == 22 || to_ == 1 || to_ == 11
      case 11 => to_ == 4 || to_ == 10 || to_ == 12 || to_ == 19
      case 12 => to_ == 7 || to_ == 11 || to_ == 16
      case _ =>
        (from_ % 3) match 
          case 0 => from_ - 1 == to_ || (12 + ((from_ / 3 - 1) * -4)) + from_ == to_
          case 1 => (9 + (from_ / 3) * -2) + from_ == to_ || from_ + 1 == to_
          case 2 => from_ + 1 == to_ || from_ - 1 == to_ || {
              val div = from_ / 3
              div match 
                case 0 => from_ + 3 == to_
                case 1 => Math.abs(from_ - to_) == 3
                case 2 => from_ - 3 == to_
            }
    canSwitch
        

object StoneMovement:
  def apply(player: IPlayer, field: IField, to: Int, from: Int): IField =
    from match
      case -1 => SetaStone().move(player, field, to, from)
      case i if (1 to 24).contains(i) =>
        if (player.pstonetoput == 0 && player.pstoneinField > 3)
          Switch().move(player, field, to, from)
        else
          Jump().move(player, field, to, from)