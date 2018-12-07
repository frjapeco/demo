package com.stratio.challenges.stratiowars.controllers

import com.stratio.challenges.stratiowars.Application
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.junit.Assert.assertEquals
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = MOCK, classes = Array(classOf[Application]))
@AutoConfigureMockMvc
class DecryptorControllerIT {

  @Autowired
  val mvc: MockMvc = null

  @Test
  @throws[Exception]
  def givenEncryptedCoordinates_whenAreSentToDecrypt_thenDecryptedCoordinatesReceived(): Unit = {
    val request = "[\"2952410b-0a94-446b-8bcb-448dc6e30b08\",\"6f9c15fa-ef51-4415-afab-36218d76c2d9\"]"
    val expectedResponse = "[\"34-10-22-edcb86430\",\"73-15-10-dc9876321\"]"
    val response = mvc.perform(post("/api/decryptor/coordinates").contentType(APPLICATION_JSON).content(request))
                      .andExpect(status.isOk)
                      .andExpect(content.contentTypeCompatibleWith(APPLICATION_JSON))
                      .andReturn
                      .getResponse
                      .getContentAsString
    assertEquals(response, expectedResponse)
  }

  @Test
  @throws[Exception]
  def givenAnIncorrectRequest_whenIsSentToDecrypt_thenBadRequestStatusReceived(): Unit = {
    val request = "{\"value\":\"bad request\"}"
    mvc.perform(post("/api/decryptor/coordinates").contentType(APPLICATION_JSON).content(request))
       .andExpect(status.isBadRequest)
  }

  @Test
  @throws[Exception]
  def givenABadFormedUuid_whenIsSentToDecrypt_thenBadRequestStatusReceived(): Unit = {
    val request = "[\"badformed-uuid\"]"
    mvc.perform(post("/api/decryptor/coordinates").contentType(APPLICATION_JSON).content(request))
      .andExpect(status.isBadRequest)
  }

}
