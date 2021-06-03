package ca.nexapp.tracing

import ca.nexapp.tracing.sentry.SentryTracer
import ca.nexapp.tracing.sfl4j.Slf4JTracer
import ca.nexapp.tracing.xray.XRayTracer
import com.amazonaws.xray.AWSXRay
import io.sentry.Sentry
import java.time.Clock

data class Settings(
    val enableXRay: Boolean,
    val enableSentry: Boolean,
    val enableLogging: Boolean
)

class TracerFactory(private val clock: Clock) {

    fun create(settings: Settings): Tracer {
        val tracers = mutableListOf<Tracer>()

        if (settings.enableXRay) {
            tracers += createXRayTracer()
        }

        if (settings.enableSentry) {
            tracers += createSentryTracer()
        }

        if (settings.enableLogging) {
            tracers += Slf4JTracer()
        }

        return MultipleTracers(tracers)
    }

    private fun createXRayTracer(): XRayTracer {
        val recorder = AWSXRay.getGlobalRecorder()
        return XRayTracer(recorder)
    }

    private fun createSentryTracer(): SentryTracer {
        val client = Sentry.getStoredClient()
        return SentryTracer(clock, client)
    }
}
