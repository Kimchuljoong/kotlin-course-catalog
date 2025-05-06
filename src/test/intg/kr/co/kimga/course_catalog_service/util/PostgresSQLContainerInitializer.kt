package kr.co.kimga.course_catalog_service.util

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@Testcontainers
open class PostgresSQLContainerInitializer {

    companion object {
        @Container
        val postgresDB =  PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:latest")).apply {
            withDatabaseName("testDb")
            withUsername("postgres")
            withPassword("postgres")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(register: DynamicPropertyRegistry) {
            register.add("spring.datasource.url", postgresDB::getJdbcUrl)
            register.add("spring.datasource.username", postgresDB::getUsername)
            register.add("spring.datasource.password", postgresDB::getPassword)
        }
    }
}