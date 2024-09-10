package com.github.emiliobarradas.test

import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestTestClientConfig {
    @Bean
    fun testClient(): TestClient = mockk<TestClient>()
}
