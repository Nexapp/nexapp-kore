package ca.nexapp.lambdas.logging

import ca.nexapp.lambdas.contexts.DefaultConfiguration
import ca.nexapp.lambdas.contexts.env.LambdaEnvLoader

class LambdaLoggingFactory(
    private val envLoader: LambdaEnvLoader
) {

    fun create(): LambdaLogger {
        val tracers = mutableListOf<LambdaLogger>()

        envLoader.tryLoad("SENTRY_DSN")?.apply {
            val sentryTracer = configureSentryLogger(this)
            tracers += sentryTracer
        }

        tracers += LambdaDefaultLogger()
        return LambdaMultipleLogger(tracers)
    }

    private fun configureSentryLogger(dsn: String): LambdaSentryLogger {
        val env = envLoader.tryLoad("ENV") ?: DefaultConfiguration.ENV
        return LambdaSentryLogger.configureDefault(dsn, env)
    }
}
