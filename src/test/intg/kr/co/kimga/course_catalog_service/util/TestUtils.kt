package kr.co.kimga.course_catalog_service.util

import kr.co.kimga.course_catalog_service.dto.CourseDTO
import kr.co.kimga.course_catalog_service.entity.Course

fun courseEntityList() = listOf(
    Course(
        null,
        "Build RestFul APIs using SpringBoot and Kotlin",
        "Development",),
    Course(
        null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot",
        "Development",),
    Course(
        null,
        "Wiremock for Java Developers",
        "Development",)
)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APIs using SpringBoot and Kotlin",
    category: String = "Kotlin",
) = CourseDTO(
    id,
    name,
    category,
)