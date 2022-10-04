package ca.nexapp.tracing.sentry

import ca.nexapp.tracing.Trace
import io.sentry.Breadcrumb
import io.sentry.IHub
import io.sentry.SentryLevel
import java.time.Instant
import java.util.Date

data class SentryTrace(
    private val hub: IHub,
    private val message: String,
    private val startOfTrace: Instant
) : Trace {

    private val breadcrumb = Breadcrumb(Date.from(startOfTrace)).apply {
        this.message = this@SentryTrace.message
        this.level = SentryLevel.INFO
        this.category = "trace"
    }

    /**
     * Puts the field in breadcrumb data
     */
    override fun setTag(name: String, value: String) {
        breadcrumb.setData(name, value)
    }

    /**
     * Puts the field in breadcrumb data
     */
    override fun setMetadata(key: String, value: String) {
        breadcrumb.setData(key, value)
    }

    /**
     * Creates and records the breadcrumb
     */
    override fun close() {
        hub.addBreadcrumb(breadcrumb)
    }

    /**
     * Changes the breadcrumb Level to ERROR
     * Puts a field "error" with exception in breadcrumb data
     */
    override fun signalError(error: Exception) {
        breadcrumb.level = SentryLevel.ERROR
        setTag("error", error.toString())
    }
}
