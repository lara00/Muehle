package de.htwg.se.Muehle.model

enum Stone(stringRepresentation: String):
  override def toString = stringRepresentation
  case White extends Stone("WHITE")
  case Black extends Stone("BLACK")
  case Empty extends Stone("EMPTY")
  case Vorbitten extends Stone("")
