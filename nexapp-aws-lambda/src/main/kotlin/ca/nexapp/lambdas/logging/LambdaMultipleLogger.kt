package ca.nexapp.lambdas.logging

import com.amazonaws.services.lambda.runtime.Context

/**
 * Combine multiple LambdaLogger together
 */
class LambdaMultipleLogger(
    val loggers: List<LambdaLogger>
) : LambdaLogger {

    override fun recordResponse(response: Any, awsRuntimeContext: Context) {
        loggers.forEach { it.recordResponse(response, awsRuntimeContext) }
    }

    override fun recordHandlerError(error: Exception, awsRuntimeContext: Context) {
        loggers.forEach { it.recordHandlerError(error, awsRuntimeContext) }
    }

    override fun recordRequest(request: Any, awsRuntimeContext: Context) {
        loggers.forEach { it.recordRequest(request, awsRuntimeContext) }
    }
}
