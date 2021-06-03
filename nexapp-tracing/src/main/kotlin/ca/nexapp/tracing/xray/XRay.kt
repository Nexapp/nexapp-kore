package ca.nexapp.tracing.xray

import ca.nexapp.logging.Logging
import com.amazonaws.xray.exceptions.AlreadyEmittedException
import com.amazonaws.xray.exceptions.SegmentNotFoundException
import com.amazonaws.xray.exceptions.SubsegmentNotFoundException
import java.io.IOException

object XRay : Logging {

    val exceptions = setOf(
        AlreadyEmittedException::class,
        SegmentNotFoundException::class,
        SubsegmentNotFoundException::class,
        IllegalStateException::class,
        IOException::class
    )

    @Throws(Exception::class)
    fun suppressXRayException(exception: Exception) {
        val shouldNotThrow = exceptions.any { it.isInstance(exception) }

        if (shouldNotThrow) {
            logger.warn(exception.message, exception)
            return
        }

        throw exception
    }
}
