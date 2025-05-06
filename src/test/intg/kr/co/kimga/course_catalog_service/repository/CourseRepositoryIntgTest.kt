package kr.co.kimga.course_catalog_service.repository

import kr.co.kimga.course_catalog_service.util.PostgresSQLContainerInitializer
import kr.co.kimga.course_catalog_service.util.courseEntityList
import kr.co.kimga.course_catalog_service.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryIntgTest: PostgresSQLContainerInitializer() {

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()

        val instructor = instructorEntity()
        instructorRepository.save(instructor)

        val courses = courseEntityList(instructor)
        courseRepository.saveAll(courses)
    }

    @Test
    fun findByNameContaining() {

        val findCourses = courseRepository.findByNameContaining("SpringBoot")
        println(findCourses)

        Assertions.assertEquals(2, findCourses.size)

    }

    @Test
    fun findCoursesbyName() {
        val findCourses = courseRepository.findCoursesbyName("SpringBoot")
        println(findCourses)

        Assertions.assertEquals(2, findCourses.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesbyName_approach2(name: String, expectedSize: Int) {
        val findCourses = courseRepository.findCoursesbyName(name)
        println(findCourses)

        Assertions.assertEquals(expectedSize, findCourses.size)

    }

    companion object {

        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("SpringBoot", 2),
                Arguments.arguments("Wiremock", 1)
            )
        }
    }
}