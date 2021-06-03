package ca.nexapp.logging

import assertk.assertions.isSuccess
import org.junit.Test
import org.slf4j.event.Level

internal class LoggerTest {

    companion object : Logging {
        private const val A_MESSAGE = "message"
        private val AN_EXCEPTION = Exception("Test")
    }

    @Test
    fun `it can log messages`() {
        val logLevels = Level.values()

        assertk.assertThat {
            logLevels.forEach { level -> logger.log(level, message = A_MESSAGE) }
        }.isSuccess()
    }

    @Test
    fun `it can log exceptions`() {
        val logLevels = Level.values()

        assertk.assertThat {
            logLevels.forEach { level -> logger.log(level, cause = AN_EXCEPTION) }
        }.isSuccess()
    }
}
