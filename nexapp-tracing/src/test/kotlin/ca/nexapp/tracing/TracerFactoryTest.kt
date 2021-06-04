package ca.nexapp.tracing

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isInstanceOf
import ca.nexapp.tracing.sentry.SentryTracer
import ca.nexapp.tracing.sfl4j.Slf4JTracer
import ca.nexapp.tracing.xray.XRayTracer
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

internal class TracerFactoryTest {

    companion object {

        private val NOW = Instant.now()
        private val CLOCK = Clock.fixed(NOW, ZoneOffset.UTC)
    }

    private val tracerFactory = TracerFactory(CLOCK)

    @Test
    fun `given logging enabled, should log to sfl4j logger`() {
        val settings = Settings(enableSentry = false, enableXRay = false, enableLogging = true)

        val tracer = tracerFactory.create(settings) as MultipleTracers

        val tracers = tracer.tracers
        assertThat(tracers).hasSize(1)
        assertThat(tracers[0]).isInstanceOf(Slf4JTracer::class)
    }

    @Test
    fun `given xray enabled, should have xray tracer`() {
        val settings = Settings(enableSentry = false, enableXRay = true, enableLogging = false)

        val tracer = tracerFactory.create(settings) as MultipleTracers

        val tracers = tracer.tracers
        assertThat(tracers).hasSize(1)
        assertThat(tracers[0]).isInstanceOf(XRayTracer::class)
    }

    @Test
    fun `given sentry enabled, should have sentry tracer`() {
        val settings = Settings(enableSentry = true, enableXRay = false, enableLogging = false)

        val tracer = tracerFactory.create(settings) as MultipleTracers

        val tracers = tracer.tracers
        assertThat(tracers).hasSize(1)
        assertThat(tracers[0]).isInstanceOf(SentryTracer::class)
    }

    @Test
    fun `given xray and sentry enabled, should have both tracers`() {
        val settings = Settings(enableSentry = true, enableXRay = true, enableLogging = false)

        val tracer = tracerFactory.create(settings) as MultipleTracers

        val tracers = tracer.tracers
        assertThat(tracers).hasSize(2)
        assertThat(tracers[0]).isInstanceOf(XRayTracer::class)
        assertThat(tracers[1]).isInstanceOf(SentryTracer::class)
    }
}
