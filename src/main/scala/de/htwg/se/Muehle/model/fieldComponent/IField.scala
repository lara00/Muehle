package de.htwg.se.Muehle.model.fieldComponent

import de.htwg.se.Muehle.model.Stone

trait IField:
  def size: Int
  def stones_field(number: Int): Stone 
  def fieldmap: Map[Int, Stone]
  def createField(cfield : Map[Int, Stone]): IField

  def setStone(number: Int, value: Stone): IField
  def deleteStone(number: Int, value: Stone): IField
  def movestone(from: Int, to: Int, value: Stone): IField

  def stoneString(number: Int): String
  def isFieldValid(intValueString: String): Boolean

  def getWhiteStonePositions: List[Int]
  def getBlackStonePositions: List[Int]
  
  override def toString(): String