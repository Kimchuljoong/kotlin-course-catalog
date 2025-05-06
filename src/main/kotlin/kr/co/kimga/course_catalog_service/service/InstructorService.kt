package kr.co.kimga.course_catalog_service.service

import kr.co.kimga.course_catalog_service.dto.InstructorDTO
import kr.co.kimga.course_catalog_service.entity.Instructor
import kr.co.kimga.course_catalog_service.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(
    private val instructorRepository: InstructorRepository
) {

    fun createInstructor(instructorDTO: InstructorDTO): Any {

        val newInstructor = instructorDTO.let {
            Instructor(
                it.id,
                it.name,
            )
        }
        val savedInstructor = instructorRepository.save(newInstructor)

        return savedInstructor.let {
            InstructorDTO(
                it.id,
                it.name,
            )
        }
    }

    fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)
    }
}