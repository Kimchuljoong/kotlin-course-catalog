package kr.co.kimga.course_catalog_service.entity

import jakarta.persistence.*

@Entity
@Table(name = "instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTRUCTOR_ID")
    val id: Int?,
    var name: String,
    @OneToMany(
        mappedBy = "instructor",
        cascade = [(CascadeType.ALL)],
        orphanRemoval = true
    )
    var courses: MutableList<Course> = mutableListOf(),
)
