package de.htwg.se.Muehle.model

enum MoveEvents:
  case SetStone
  case SetStone_Mill
  case MoveStone
  case MoveStone_Mill
  case NoMove

enum MillEvents:
  case DeleteStone
  case EndGame
  case WrongDelete

