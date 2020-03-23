package ca.nexapp.lambdas.logging

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isInstanceOf
import ca.nexapp.lambdas.contexts.env.TestEnvLoader
import org.junit.jupiter.api.Test

internal class LambdaLoggingFactoryTest {

    @Test
    fun `given no SENTRY_DSN, should not configure sentry logger`() {
        val envLoader = TestEnvLoader(emptyMap())
        val factory = LambdaLoggingFactory(envLoader)

        val logger = factory.create() as LambdaMultipleLogger

        val loggers = logger.loggers
        assertThat(loggers).hasSize(1)
        assertThat(loggers[0]).isInstanceOf(LambdaDefaultLogger::class)
    }

    @Test
    fun `given SENTRY_DSN, should configure sentry logger`() {
        val envLoader = TestEnvLoader(mapOf("SENTRY_DSN" to ""))
        val factory = LambdaLoggingFactory(envLoader)

        val logger = factory.create() as LambdaMultipleLogger

        val loggers = logger.loggers
        assertThat(loggers).hasSize(2)
        assertThat(loggers[0]).isInstanceOf(LambdaSentryLogger::class)
        assertThat(loggers[1]).isInstanceOf(LambdaDefaultLogger::class)
    }
}
