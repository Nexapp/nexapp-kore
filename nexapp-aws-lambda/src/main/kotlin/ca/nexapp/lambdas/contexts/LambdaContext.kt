package ca.nexapp.lambdas.contexts

import ca.nexapp.lambdas.contexts.env.LambdaEnvLoader
import ca.nexapp.lambdas.contexts.env.LambdaEnvLoaderFactory
import ca.nexapp.lambdas.logging.LambdaLoggingFactory
import ca.nexapp.lambdas.tracing.LambdaTracingFactory
import ca.nexapp.tracing.TracerFactory
import java.time.Clock

object DefaultConfiguration {
    const val ENV = "test"
}

/**
 * Extensible Lambda Context
 *
 * Comes with a LocalEnv => Ssm EnvLoader by default
 *
 * @param envLoader Overrides the default EnvLoader
 */
open class LambdaContext(
    private val clock: Clock = Clock.systemUTC(),
    private val envLoader: LambdaEnvLoader = LambdaEnvLoaderFactory().create()
) {

    val logger by lazy { loggingFactory.create() }

    val tracer by lazy { tracingFactory.createTracer() }

    /**
     * Lambda tracing factory, configured with Sentry and XRAY if `SENTRY_DSN` and `XRAY_TRACING_ENABLED` is setup
     */
    open val tracingFactory by lazy { LambdaTracingFactory(envLoader, TracerFactory(clock)) }

    /**
     * Lambda logging factory, configured with Log4J2 and Sentry if `SENTRY_DSN` is present
     */
    open val loggingFactory by lazy { LambdaLoggingFactory(envLoader) }
}
