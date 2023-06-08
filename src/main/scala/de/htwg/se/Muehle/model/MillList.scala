package de.htwg.se.Muehle.model

object MillList {
  private var mill: List[Int] = Nil

  def list: List[Int] = mill
  def deleteElement(): Unit = mill = mill.tail
  def add_elementint(element: Int): Unit = mill = element :: mill
}