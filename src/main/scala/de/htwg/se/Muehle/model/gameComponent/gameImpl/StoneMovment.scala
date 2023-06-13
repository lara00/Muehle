package de.htwg.se.Muehle.model.gameComponent.gameImpl

import de.htwg.se.Muehle.model.playerComponent.Player
import de.htwg.se.Muehle.model.fieldComponent.Field

object StoneMovement:
  def apply(player: Player, field: Field, to: Int, from: Int): Field =
    from match
      case -1 => field.setStone(to, player.name)
      case i if (1 to 24).contains(i) =>
        if (player.stonetoput == 0 && player.stoneintheField > 3)
          if (switch(from, to)) field.movestone(from, to, player.name) else field
        else
          field.movestone(from, to, player.name)

  /*1          2        3
      4      5     6
          7  8  9
  10  11  12    13 14 15
          16 17 18
      19     20    21
  22         23       24

  Field 1 is symmetric to field 24, field 2 to field 23, field 3 to field 22, and so on.
  Any two adjacent fields always have the same possible ways to move to the next field, but with different signs.
  For example, it is possible to move from field 1 to field 2, and from field 1 to field 10 by adding 1 (+1) and 9 (+9), respectively.
  Similarly, we can use the symmetry of a Mühle field to calculate the numbers between fields. For example, since 25 - 24 = 1 and 25 - 13 = 12,
  we can use the option from field 1 to field 12 to calculate the numbers from field 13 to field 24.
   */
  private def switch(from: Int, to: Int): Boolean = 
    val from_ = if (from > 12) 25 - from else from
    val to_ = if (from > 12) 25 - to else to
    val canSwitch = from_ match
      /*The other fields, 10, 11, and 12, are compared with the allowed fields to which they may move.*/
      case 10 => to_ == 22 || to_ == 1 || to_ == 11
      case 11 => to_ == 4 || to_ == 10 || to_ == 12 || to_ == 19
      case 12 => to_ == 7 || to_ == 11 || to_ == 16
      /*
      The fields 1, 4, 7 and 2, 5, 8, and 3, 6, 9 are also interesting.
      We can use the symmetry to create a formula to calculate the allowed fields number
      [between 1 and 9] % 3. For example, 9 % 3 = 0 or 3 remainder 0.
      Rest 1 | 1 (+1, +9) - 4 (-1, +7) - 7 (+1, +5) // (9 + (from_ / 3) [integer result] * -2) + from_
      Rest 2 | 2 (-+1, +3) - 5 (+-1, +-3) - 8 (+-1, -3) // No simplifying formula found yet
      Rest 0 | 3 (+1, +12) - 6 (-1, +8) - 9 (+1, +9) // (12 + (from / 3) [integer result] * -4) + from*/
      case _ =>
        (from_ % 3) match 
          case 0 =>
            from_ - 1 == to_ || (12 + ((from_ / 3 - 1) * -4)) + from_ == to_
          case 1 =>
            (9 + (from_ / 3) * -2) + from_ == to_ || from_ + 1 == to_
          case 2 =>
            from_ + 1 == to_ || from_ - 1 == to_ || {
              val div = from_ / 3
              div match 
                case 0 => from_ + 3 == to_
                case 1 => Math.abs(from_ - to_) == 3
                case 2 => from_ - 3 == to_
            }
    canSwitch


