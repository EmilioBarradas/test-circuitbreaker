package com.github.emiliobarradas.test

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class TestApplication

fun main() {
    runApplication<TestApplication>()
}

@RestController
@RequestMapping("/test")
class TestController(
    private val testService: TestService,
) {
    @GetMapping
    fun test(): Map<String, String> = testService.test()
}

@Service
class TestService(
    private val testClient: TestClient,
) {
    fun test(): Map<String, String> = testClient.test()
}

open class TestClient {
    @CircuitBreaker(name = "testClientCircuitBreaker")
    open fun test(): Map<String, String> {
        error("oops")
    }
}

@Profile("!integration-test")
@Configuration
class TestClientConfig {
    @Bean
    fun testClient(): TestClient = TestClient()
}
