import java.lang.reflect.Field
import scala.runtime.IntRef
import module._

private def switch(from: Int, to: Int): Boolean = {
  /*
    Field 1 is symmetric to field 24, field 2 to field 23, field 3 to field 22, and so on.
    Any two adjacent fields always have the same possible ways to move to the next field, but with different signs.
    For example, it is possible to move from field 1 to field 2, and from field 1 to field 10 by adding 1 (+1) and 9 (+9), respectively.
    Similarly, we can use the symmetry of a Mühle field to calculate the numbers between fields. For example, since 25 - 24 = 1 and 25 - 13 = 12,
    we can use the option from field 1 to field 12 to calculate the numbers from field 13 to field 24.
   */
  val from_ = if (from > 12) 25 - from else from
  val to_ = if (from > 12) 25 - to else to
  val canSwitch = from_ match {
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
      (from_ % 3) match {
        case 0 =>
          from_ - 1 == to_ || (12 + ((from_ / 3 - 1) * -4)) + from_ == to_
        case 1 =>
          (9 + (from_ / 3) * -2) + from_ == to_ || from_ + 1 == to_
        case 2 =>
          from_ + 1 == to_ || from_ - 1 == to_ || {
            val div = from_ / 3
            div match {
              case 0 => from_ + 3 == to_
              case 1 => Math.abs(from_ - to_) == 3
              case 2 => from_ - 3 == to_
            }
          }
      }
  }
  canSwitch
}

val a = switch(1, 2)
val b = switch(1, 10)
val c = switch(2, 1)
val d = switch(2, 3)
val e = switch(2, 5)
val ab = switch(3, 2)
val ac = switch(3, 15)
val f = switch(4, 5)
val g = switch(4, 11)
val h = switch(5, 4)
val i = switch(5, 6)
val j = switch(5, 2)
val ar = switch(5, 8)
val k = switch(6, 5)
val l = switch(6, 14)
val ka = switch(7, 12)
val lb = switch(7, 8)
val kc = switch(8, 7)
val ld = switch(8, 5)
val ke = switch(8, 9)
val lf = switch(9, 8)
val kg = switch(9, 13)
val lh = switch(10, 1)
val kca = switch(8, 7)
val ldb = switch(8, 5)
val kec = switch(8, 9)
val lfd = switch(9, 8)
val kge = switch(9, 13)
val lhf = switch(10, 1)
val ldx = switch(10, 11)
val key = switch(10, 22)
val lfz = switch(11, 10)
val kgi = switch(11, 12)
val lho = switch(11, 4)
val lhp = switch(11, 19)
val kgz = switch(12, 7)
val lht = switch(12, 11)
val lhr = switch(12, 16)

val aa = switch(13, 9)
val bb = switch(13, 14)
val cc = switch(13, 18)
val dd = switch(14, 6)
val ea = switch(14, 13)
val aba = switch(14, 15)
val aca = switch(14, 21)
val fa = switch(15, 3)
val ga = switch(15, 14)
val ha = switch(15, 24)
val ia = switch(16, 12)
val ja = switch(16, 17)
val ara = switch(17, 16)
val kaer = switch(17, 18)
val la = switch(17, 20)
val kaa = switch(18, 13)
val lba = switch(18, 17)
val kcaadw = switch(19, 11)
val lda = switch(19, 20)
val kea = switch(20, 19)
val lfa = switch(20, 17)
val kga = switch(20, 21)
val lha = switch(20, 23)
val kcaa = switch(21, 20)
val ldba = switch(21, 14)
val keca = switch(22, 10)
val lfda = switch(22, 23)
val kgea = switch(23, 22)
val lhfa = switch(23, 24)
val ldxa = switch(24, 23)
val keya = switch(24, 15)
