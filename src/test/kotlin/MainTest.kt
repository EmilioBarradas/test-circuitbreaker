package com.github.emiliobarradas.test

import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.runApplication
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import kotlin.test.assertEquals

class MainTest {
    private lateinit var testClient: TestClient

    @BeforeEach
    fun setup() {
        val applicationContext = runApplication<TestApplication>("--spring.profiles.active=integration-test")
        testClient = applicationContext.getBean(TestClient::class.java)
    }

    @Test
    fun test() {
        every { testClient.test() } returns mapOf("test" to "test")

        val template = RestTemplate()
        val response = template.getForEntity<String>("http://localhost:8080/test")

        assert(response.statusCode.is2xxSuccessful)
        assertEquals("{\"test\":\"test\"}", response.body)
    }
}
