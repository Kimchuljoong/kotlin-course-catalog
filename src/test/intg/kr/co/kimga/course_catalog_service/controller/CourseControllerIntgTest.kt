package kr.co.kimga.course_catalog_service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.kimga.course_catalog_service.dto.CourseDTO
import kr.co.kimga.course_catalog_service.entity.Course
import kr.co.kimga.course_catalog_service.repository.CourseRepository
import kr.co.kimga.course_catalog_service.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {

        val courseDTO = CourseDTO(null, "Build Restful APIs using SpringBoot and Kotlin", "study")

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
        val coursesDTOs = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("coursesDTO: $coursesDTOs")
        Assertions.assertEquals(3, coursesDTOs!!.size)
    }

    @Test
    fun retrieveAllCourses_Byname() {

        val uri = UriComponentsBuilder.fromUriString("/v1/courses")
            .queryParam("course_name", "SpringBoot")
            .toUriString()

        val coursesDTOs = webTestClient.get()
            .uri(uri)
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
        courseRepository.save(course)

        val courseDTO = CourseDTO(
            null,
            "Build Restful APIs using SpringBoot and Kotlin1",
            "Development"
        )

        val updatedCourse = webTestClient.put()
            .uri("/v1/courses/{courseId}", course.id)
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

        val course = Course(
            null,
            "Build Restful APIs using SpringBoot and Kotlin",
            "Development"
        )
        courseRepository.save(course)

        webTestClient.delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent
    }
}