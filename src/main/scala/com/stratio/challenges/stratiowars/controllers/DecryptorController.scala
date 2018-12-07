package com.stratio.challenges.stratiowars.controllers

import com.stratio.challenges.stratiowars.services.DecryptorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/decryptor"))
class DecryptorController @Autowired()(decryptorService: DecryptorService) {

  @PostMapping(Array("/coordinates"))
  def decryptCoordinates(@RequestBody coordinates: Array[String]): Array[String] = decryptorService.decryptAll(coordinates)

}
