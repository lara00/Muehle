package de.htwg.se.Muehle.model

enum Stone(string: String):
  override def toString = string
  case White extends Stone("WHITE")
  case Black extends Stone("BLACK")
  case Empty extends Stone("EMPTY")
  case Vorbitten extends Stone("")
