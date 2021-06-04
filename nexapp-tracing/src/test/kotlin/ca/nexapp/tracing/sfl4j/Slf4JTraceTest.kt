package ca.nexapp.tracing.sfl4j

import io.mockk.Ordering
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.event.Level

internal class Slf4JTraceTest {

    companion object {
        const val ID = "12345"
        const val NAME = "Name"
    }

    private val logger = mockk<Logger>(relaxed = true)

    @BeforeEach
    internal fun setUp() {
        clearMocks(logger)
    }

    @Test
    fun `given signal error, when close, should log error at configured level`() {
        val error = Exception()

        trace(errorLevel = Level.WARN).use {
            it.signalError(error)
        }
        trace(errorLevel = Level.TRACE).use {
            it.signalError(error)
        }

        verify(ordering = Ordering.ORDERED) {
            logger.warn(any(), error)
            logger.trace(any(), error)
        }
    }

    @Test
    fun `given no signal error, when close, should log info`() {
        trace(errorLevel = Level.WARN).use { }

        verify { logger.info(any()) }
    }

    private fun trace(errorLevel: Level = Level.INFO): Slf4JTrace {
        return Slf4JTrace(logger, ID, NAME, errorLevel = errorLevel)
    }
}
