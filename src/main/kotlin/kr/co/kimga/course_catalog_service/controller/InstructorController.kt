package kr.co.kimga.course_catalog_service.controller

import jakarta.validation.Valid
import kr.co.kimga.course_catalog_service.dto.InstructorDTO
import kr.co.kimga.course_catalog_service.entity.Instructor
import kr.co.kimga.course_catalog_service.service.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(
    private val instructorService: InstructorService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@RequestBody @Valid instructorDTO: InstructorDTO) =
        instructorService.createInstructor(instructorDTO)

}