package ca.nexapp.lambdas.logging

import com.amazonaws.services.lambda.runtime.Context
import io.sentry.*

/**
 * Sentry logger for the lambda lifecycle
 *
 * Records breadcrumbs and extras akin to the Python integration
 * Limitations : relies on the Log4J2 integration to actually send errors/warnings to Sentry
 */
class LambdaSentryLogger : LambdaLogger {

    companion object {

        fun configureDefault(dsn: String, env: String): LambdaSentryLogger {
            Sentry.init(dsn)

            Sentry.OptionsConfiguration<SentryOptions> { options ->
                options.environment = env
            }

            return LambdaSentryLogger()
        }
    }

    override fun recordHandlerError(error: Exception, awsRuntimeContext: Context) {
        val breadcrumb = Breadcrumb().apply {
            this.category = "error"
            this.level = SentryLevel.ERROR
            this.message = error.message
        }.addAwsContextMetadata(awsRuntimeContext)

        Sentry.addBreadcrumb(breadcrumb)
        Sentry.setExtra("error", error.message ?: "")
    }

    override fun recordResponse(response: Any, awsRuntimeContext: Context) {
        val breadcrumb = Breadcrumb().apply {
            this.category = "response"
            this.level = SentryLevel.INFO
            setData("content", response.toString())
        }.addAwsContextMetadata(awsRuntimeContext)

        Sentry.addBreadcrumb(breadcrumb)
        Sentry.setExtra("response", response.toString())
    }

    override fun recordRequest(request: Any, awsRuntimeContext: Context) {
        val breadcrumb = Breadcrumb().apply {
            this.category = "request"
            this.level = SentryLevel.INFO
            setData("content", request.toString())
        }.addAwsContextMetadata(awsRuntimeContext)

        Sentry.setExtra("request_id", awsRuntimeContext.awsRequestId)
        Sentry.setExtra("function_name", awsRuntimeContext.functionName)
        Sentry.setExtra("function_version", awsRuntimeContext.functionVersion)
        Sentry.setExtra("memory_limit", awsRuntimeContext.memoryLimitInMB.toString())
        Sentry.setExtra("maximum_execution_time", awsRuntimeContext.remainingTimeInMillis.toString())

        Sentry.addBreadcrumb(breadcrumb)
        Sentry.setExtra("request", request.toString())
    }

    private fun Breadcrumb.addAwsContextMetadata(awsRuntimeContext: Context) = apply {
        setData("request_id", awsRuntimeContext.awsRequestId)
        setData("time_remaining", "${awsRuntimeContext.remainingTimeInMillis}ms")
    }
}
