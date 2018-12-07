package com.stratio.challenges.stratiowars.services

import java.lang.Integer.parseInt

import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service

@Service
class DecryptorService {

  private val HEX = 16

  def decryptAll(coordinates: Array[String]): Array[String] = coordinates.map(decrypt)

  def decrypt(coordinate: String): String = {
    val uuidPattern = "^[a-f,0-9]{8}-[a-f,0-9]{4}-[a-f,0-9]{4}-[a-f,0-9]{4}-[a-f,0-9]{12}$".r
    uuidPattern.findFirstMatchIn(coordinate) match {
      case Some(_) => {
        val coords = coordinate.split("-")
        val galaxy = decryptGalaxy(coords(0))
        val quadrant = decryptQuadrant(coords(1))
        val starSystem = decryptStarSystem(coords(2),coords(3))
        val planet = decryptPlanet(coords(4))
        s"$galaxy-$quadrant-$starSystem-$planet"
      }
      case None => throw new HttpMessageNotReadableException("Invalid uuid format")
    }
  }

  private def decryptGalaxy(galaxy: String): Integer = galaxy.split("").map(x => parseInt(x,HEX)).sum

  private def decryptQuadrant(quadrant: String): Integer = quadrant.split("").map(x => parseInt(x,HEX)).sortWith(_ > _)(0)

  private def decryptStarSystem(starSystem1: String, starSystem2: String): Integer = {
    val maxStarSystem1 = starSystem1.split("").map(x => parseInt(x,HEX)).sortWith(_ > _)(0)
    val maxStarSystem2 = starSystem1.split("").map(x => parseInt(x,HEX)).sortWith(_ > _)(0)
    maxStarSystem1 + maxStarSystem2
  }

  private def decryptPlanet(value: String): String = value.distinct.sortWith(_ > _)

}
