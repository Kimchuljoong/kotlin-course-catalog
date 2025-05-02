package kr.co.kimga.course_catalog_service.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kr.co.kimga.course_catalog_service.service.GreetingService
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    @Test
    fun retrieveGreeting() {

        val name = "kim"

        every {
            greetingServiceMock.retrieveGreeting(any())
        } returns "$name, Hello from default profile"

        val result = webTestClient.get()
            .uri("/v1/greeting/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("$name, Hello from default profile", result.responseBody)

    }
}