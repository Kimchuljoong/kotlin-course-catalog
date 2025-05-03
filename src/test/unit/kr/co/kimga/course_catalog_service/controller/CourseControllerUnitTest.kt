package kr.co.kimga.course_catalog_service.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import kr.co.kimga.course_catalog_service.dto.CourseDTO
import kr.co.kimga.course_catalog_service.entity.Course
import kr.co.kimga.course_catalog_service.service.CourseService
import kr.co.kimga.course_catalog_service.util.courseDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMockk: CourseService


    @Test
    fun addCourse() {

        val courseDTO = CourseDTO(null, "Build Restful APIs using SpringBoot and Kotlin", "study")

        every {
            courseServiceMockk.addCourse(any())
        }.returns(courseDTO(id = 1))

        val savedCourseDTO = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {

        every {
            courseServiceMockk.retrieveAllCourses()
        }.returnsMany(
            listOf(
                courseDTO(id = 1),
                courseDTO(
                    id = 2,
                    name = "Build Reactive Microservices using Spring Webflux/SpringBoot")
            )
        )

        val coursesDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("coursesDTO: $coursesDTOs")
        Assertions.assertEquals(2, coursesDTOs!!.size)
    }


    @Test
    fun updateCourse() {

        val course = Course(
            null,
            "Build Restful APIs using SpringBoot and Kotlin",
            "Development"
        )

        every {
            courseServiceMockk.updateCourse(any(), any())
        }.returns(
            courseDTO(
                id = 100,
                name = "Build Restful APIs using SpringBoot and Kotlin1",
                ),
        )

        val courseDTO = CourseDTO(
            null,
            "Build Restful APIs using SpringBoot and Kotlin1",
            "Development"
        )

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Build Restful APIs using SpringBoot and Kotlin1", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {

        every {
            courseServiceMockk.deleteCourse(any())
        }.just(
            runs
        )

        webTestClient.delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
    }


    @Test
    fun addCourse_validation() {

        val courseDTO = CourseDTO(
            null, "", "")

        every {
            courseServiceMockk.addCourse(any())
        }.returns(courseDTO(id = 1))

        val response = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("courseDTO.category must not be blank, courseDTO.name must not be blank", response)
    }

    @Test
    fun addCourse_runtimeException() {

        val courseDTO = CourseDTO(
            null,
            name = "Build Restful APIs using SpringBoot and Kotlin",
            "Development")

        val errorMessage = "Unexpected Error occurred"
        every {
            courseServiceMockk.addCourse(any())
        }.throws(RuntimeException(errorMessage))

        val response = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(errorMessage, response)
    }
}