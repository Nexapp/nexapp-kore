package ca.nexapp.tracing.sfl4j

import ca.nexapp.logging.log
import ca.nexapp.tracing.Trace
import org.slf4j.Logger
import org.slf4j.event.Level
import java.time.Clock
import java.time.Duration

class Slf4JTrace(
    private val logger: Logger,
    private val id: String,
    private val name: String,
    private val errorLevel: Level = Level.INFO,
    private val clock: Clock = Clock.systemUTC()
) : Trace {

    private val startedAt = clock.instant()

    private val tags: MutableMap<String, String> = mutableMapOf()
    private val metadata: MutableMap<String, String> = mutableMapOf()

    private var error: Exception? = null

    override fun signalError(error: Exception) {
        this.error = error
    }

    override fun setTag(name: String, value: String) {
        tags[name] = value
    }

    override fun setMetadata(key: String, value: String) {
        metadata[key] = value
    }

    override fun close() {
        val closedAt = clock.instant()
        val totalDuration = Duration.between(startedAt, closedAt)

        val message = "Trace [$id] - $name - $totalDuration - $tags - $metadata"

        when (val currentError = error) {
            null -> logger.info(message)
            else -> logger.log(errorLevel, cause = currentError, message = message)
        }
    }
}
