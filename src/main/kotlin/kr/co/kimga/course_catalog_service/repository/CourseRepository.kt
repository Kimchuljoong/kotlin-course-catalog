package kr.co.kimga.course_catalog_service.repository

import kr.co.kimga.course_catalog_service.entity.Course
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository: CrudRepository<Course, Int> {
}