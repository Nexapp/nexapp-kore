package ca.nexapp.tracing.sentry

import ca.nexapp.tracing.Trace
import ca.nexapp.tracing.Tracer
import io.sentry.Sentry
import java.time.Clock

class SentryTracer(
    private val clock: Clock,
) : Tracer {

    override fun openTrace(name: String): Trace {
        val now = clock.instant()

        return SentryTrace(
            hub = Sentry.getCurrentHub(),
            message = name,
            startOfTrace = now
        )
    }
}
