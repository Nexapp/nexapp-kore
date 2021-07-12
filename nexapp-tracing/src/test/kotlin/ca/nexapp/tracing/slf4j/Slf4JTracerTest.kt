package ca.nexapp.tracing.slf4j

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class Slf4JTracerTest {

    companion object {
        private const val TRACE_NAME = "Bob"
    }

    private val tracer = Slf4JTracer()

    @Test
    fun `should execute in trace`() {
        val value = tracer.trace(TRACE_NAME) { "ShouldBeReturned" }
        assertThat(value).isEqualTo("ShouldBeReturned")
    }
}
