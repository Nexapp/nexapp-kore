package ca.nexapp.tracing.sentry

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.sentry.Sentry
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

internal class SentryTracerTest {

    companion object {
        private val NOW = Instant.now()
        private val CLOCK = Clock.fixed(NOW, ZoneOffset.UTC)
    }

    @Test
    fun `should create sentry trace`() {
        val tracer = SentryTracer(CLOCK)

        val trace = tracer.openTrace("Bob")

        val expected = SentryTrace(
            hub = Sentry.getCurrentHub(),
            title = "Bob",
            startOfTrace = NOW
        )
        assertThat(trace).isEqualTo(expected)
    }
}
