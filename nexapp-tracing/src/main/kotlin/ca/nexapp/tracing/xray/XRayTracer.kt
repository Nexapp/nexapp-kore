package ca.nexapp.tracing.xray

import ca.nexapp.tracing.Trace
import ca.nexapp.tracing.Tracer
import com.amazonaws.xray.AWSXRayRecorder
import com.amazonaws.xray.entities.Subsegment

class XRayTracer(
    private val recorder: AWSXRayRecorder
) : Tracer {

    override fun openTrace(name: String): Trace {
        val subsegment = createSubsegment(name)
        return XRayTrace(subsegment)
    }

    private fun createSubsegment(name: String): Subsegment? {
        return try {
            recorder.beginSubsegment(name)
        } catch (expected: Exception) {
            XRay.suppressXRayException(expected)
            null
        }
    }
}
