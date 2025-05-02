package kr.co.kimga.course_catalog_service.controller

import kr.co.kimga.course_catalog_service.service.GreetingService
import mu.KLogger
import mu.KotlinLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
@RequestMapping("/v1/greeting")
class GreetingController(
    val greetingService: GreetingService
) {
    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String {
        logger.info {"Name is $name"}
//        return "Hello $name"
        return greetingService.retrieveGreeting(name)
    }

}