package kr.co.kimga.course_catalog_service.service

import kr.co.kimga.course_catalog_service.dto.CourseDTO
import kr.co.kimga.course_catalog_service.entity.Course
import kr.co.kimga.course_catalog_service.exception.CourseNotFoundException
import kr.co.kimga.course_catalog_service.repository.CourseRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val newCourse = courseDTO.let {
            Course(null, it.name, it.category)
        }

        val savedCourse = courseRepository.save(newCourse)

        logger.info { "saved course is $savedCourse" }

        return savedCourse.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    fun retrieveAllCourses(): List<CourseDTO> {
        return courseRepository.findAll()
            .map {
                CourseDTO(it.id, it.name, it.category)
            }
    }

    @Transactional
    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val findCourse = courseRepository.findById(courseId)
            .orElseThrow { CourseNotFoundException("Course not found: $courseId") }

        findCourse.let {
            it.name = courseDTO.name
            it.category = courseDTO.category
        }

        return CourseDTO(findCourse.id, findCourse.name, findCourse.category)
    }

    @Transactional
    fun deleteCourse(courseId: Int) {

        val findCourse = courseRepository.findById(courseId)
            .orElseThrow { CourseNotFoundException("course not found: $courseId") }

        courseRepository.deleteById(findCourse.id!!)
    }
}