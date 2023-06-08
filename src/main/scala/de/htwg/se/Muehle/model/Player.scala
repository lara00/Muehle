package de.htwg.se.Muehle.model

case class Player(name: Stone, stonetoput: Int, stoneintheField: Int) :
  def stonetoputinthefield: Player = 
    this.copy(stonetoput = stonetoput - 1)

  def incrementStoneintheField: Player =
    this.copy(stoneintheField = stoneintheField + 1)

  def decrementStoneintheField: Player =
    this.copy(stoneintheField = stoneintheField - 1)

  def setstone(field: Field, aktiveplayer: Player, fieldenummber: Int): Field =
    field.setStone(fieldenummber, aktiveplayer.name)
