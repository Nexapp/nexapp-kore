package ca.nexapp.lambdas.logging

import ca.nexapp.lambdas.io.LambdaObjectMapper
import com.amazonaws.services.lambda.runtime.Context
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.kotlin.Logging

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
        logger.log(level, "Request : $request")
        logRuntimeContext(awsRuntimeContext)
    }

    override fun recordResponse(response: Any, awsRuntimeContext: Context) {
        logger.log(level, "Response : $response")
    }

    override fun recordHandlerError(error: Exception, awsRuntimeContext: Context) {
        logger.error("Error", error)
    }

    private fun logRuntimeContext(awsRuntimeContext: Context) {
        logger.log(level, "Context : ${objectMapper.writeValueAsString(awsRuntimeContext)}")
    }
}
