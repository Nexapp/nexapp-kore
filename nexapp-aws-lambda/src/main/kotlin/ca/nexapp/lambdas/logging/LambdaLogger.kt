package ca.nexapp.lambdas.logging

import com.amazonaws.services.lambda.runtime.Context

/**
 * LambdaLogger interface to log lifecycle information
 */
interface LambdaLogger {

    /**
     * @param request serializable lambda request
     */
    fun recordRequest(request: Any, awsRuntimeContext: Context)

    /**
     * @param response serializable lambda response
     */
    fun recordResponse(response: Any, awsRuntimeContext: Context)

    /**
     * @param error caught error during lambda execution
     */
    fun recordHandlerError(error: Exception, awsRuntimeContext: Context)
}
