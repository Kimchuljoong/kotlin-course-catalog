package kr.co.kimga.course_catalog_service.repository

import kr.co.kimga.course_catalog_service.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var repository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        val courseEntityList = courseEntityList()
        courseRepository.saveAll(courseEntityList)
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