package ca.nexapp.tracing.sfl4j

import ca.nexapp.logging.Logging
import ca.nexapp.tracing.Trace
import ca.nexapp.tracing.Tracer
import java.util.UUID

class Slf4JTracer : Tracer {

    companion object : Logging

    override fun openTrace(name: String): Trace {
        val traceId = UUID.randomUUID().toString()

        return Slf4JTrace(
            logger = logger,
            id = traceId,
            name = name
        )
    }
}
