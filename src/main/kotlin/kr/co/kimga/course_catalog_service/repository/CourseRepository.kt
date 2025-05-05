package kr.co.kimga.course_catalog_service.repository

import kr.co.kimga.course_catalog_service.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository: CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String): List<Course>

    @Query(value = "SELECT * FROM COURSES WHERE NAME LIKE %?1%", nativeQuery = true)
    fun findCoursesbyName(courseName: String): List<Course>
}