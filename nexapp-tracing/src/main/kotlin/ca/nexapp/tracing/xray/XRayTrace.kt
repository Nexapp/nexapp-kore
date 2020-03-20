package ca.nexapp.tracing.xray

import ca.nexapp.tracing.Trace
import com.amazonaws.xray.entities.Entity

data class XRayTrace(
    private val xrayEntity: Entity?
) : Trace {

    /**
     * Puts exception to XRay Trace
     */
    override fun signalError(error: Exception) {
        try {
            xrayEntity?.addException(error)
        } catch (expected: Exception) {
            XRay.suppressXRayException(expected)
        }
    }

    /**
     * Puts annotation to XRay trace
     */
    override fun setTag(name: String, value: String) {
        try {
            xrayEntity?.putAnnotation(name, value)
        } catch (expected: Exception) {
            XRay.suppressXRayException(expected)
        }
    }

    /**
     * Puts metadata to XRay trace
     */
    override fun setMetadata(key: String, value: String) {
        try {
            xrayEntity?.putMetadata(key, value)
        } catch (expected: Exception) {
            XRay.suppressXRayException(expected)
        }
    }

    /**
     * Closes the entity
     */
    override fun close() {
        try {
            xrayEntity?.close()
        } catch (expected: Exception) {
            XRay.suppressXRayException(expected)
        }
    }
}
