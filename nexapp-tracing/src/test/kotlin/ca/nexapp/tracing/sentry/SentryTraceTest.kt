package ca.nexapp.tracing.sentry

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.sentry.Breadcrumb
import io.sentry.IHub
import io.sentry.SentryLevel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

internal class SentryTraceTest {

    companion object {
        private val NOW = Instant.now()
        private const val MESSAGE = "Bob"
    }

    private val hub = mockk<IHub>(relaxed = true)

    @BeforeEach
    internal fun setUp() {
        clearMocks(hub)
    }

    @Test
    fun `when close, should build breadcrumb with data as tags and metadata`() {
        SentryTrace(hub, MESSAGE, NOW).use {
            it.setTag("Tag1", "TagValue1")
            it.setMetadata("Meta1", "MetaValue1")
        }

        val expectedData = mapOf(
            "Tag1" to "TagValue1",
            "Meta1" to "MetaValue1"
        )
        val breadcrumb = slot<Breadcrumb>()
        verify { hub.addBreadcrumb(capture(breadcrumb)) }
        assertThat(breadcrumb.captured.data).isEqualTo(expectedData)
    }

    @Test
    fun `given error signaled, when close, should have error in data`() {
        val error = Exception("Error")

        SentryTrace(hub, MESSAGE, NOW).use { it.signalError(error) }

        val expectedData = mapOf("error" to error.toString())
        val breadcrumb = slot<Breadcrumb>()
        verify { hub.addBreadcrumb(capture(breadcrumb)) }
        assertThat(breadcrumb.captured.level).isEqualTo(SentryLevel.ERROR)
        assertThat(breadcrumb.captured.data).isEqualTo(expectedData)
    }
}
