package kr.co.kimga.course_catalog_service.util

import kr.co.kimga.course_catalog_service.dto.CourseDTO
import kr.co.kimga.course_catalog_service.entity.Course
import kr.co.kimga.course_catalog_service.entity.Instructor

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

fun courseEntityList(instructor: Instructor? = null) = listOf(
    Course(
        null,
        "Build RestFul APIs using SpringBoot and Kotlin",
        "Development",
        instructor,
        ),
    Course(
        null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot",
        "Development",
        instructor,
        ),
    Course(
        null,
        "Wiremock for Java Developers",
        "Development",
        instructor,
        )
)

fun instructorEntity(name: String = "kimchj") =
    Instructor(null, name)