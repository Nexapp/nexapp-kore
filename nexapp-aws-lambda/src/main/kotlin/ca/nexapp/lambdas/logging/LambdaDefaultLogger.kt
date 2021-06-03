package ca.nexapp.lambdas.logging

import ca.nexapp.lambdas.io.LambdaObjectMapper
import ca.nexapp.logging.Logging
import ca.nexapp.logging.log
import com.amazonaws.services.lambda.runtime.Context
import org.slf4j.event.Level

/**
 * Log4J2 lambda lifecycle logger
 *
 * Records event to display in your preferred integration
 */
class LambdaDefaultLogger(
    private val level: Level = Level.DEBUG,
    private val objectMapper: LambdaObjectMapper = LambdaObjectMapper()
) : LambdaLogger {

    companion object : Logging

    override fun recordRequest(request: Any, awsRuntimeContext: Context) {
        logger.log(level, message = "Request : $request")
        logRuntimeContext(awsRuntimeContext)
    }

    override fun recordResponse(response: Any, awsRuntimeContext: Context) {
        logger.log(level, message = "Response : $response")
    }

    override fun recordHandlerError(error: Exception, awsRuntimeContext: Context) {
        logger.error("Error", error)
    }

    private fun logRuntimeContext(awsRuntimeContext: Context) {
        logger.log(level, message = "Context : ${objectMapper.writeValueAsString(awsRuntimeContext)}")
    }
}
