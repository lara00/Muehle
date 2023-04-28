package de.htwg.se.Muehle
package util

trait Observer:
  def update(e: Event): Unit

trait Observable:
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer) = subscribers = subscribers :+ s
  def notifyObservers(e: Event) = subscribers.foreach(o => o.update(e))

enum Event:
  case Quit
  case Set
  case Status
