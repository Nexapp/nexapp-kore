package ca.nexapp.lambdas.contexts.env

import ca.nexapp.logging.Logging
import software.amazon.awssdk.services.ssm.SsmClient

/**
 * Creates the default EnvLoader which fallbacks to SSM if not found in local environment variables
 */
class LambdaEnvLoaderFactory(
    private val ssmPath: String? = System.getenv("SSM_PATH"),
    private val ssm: SsmClient = SsmClient.create()
) {

    companion object : Logging

    fun create(): LambdaEnvLoader {
        val loaders = mutableListOf<LambdaEnvLoader>(
            LocalEnvLoader()
        )

        ssmPath?.apply {
            logger.info("Using SSM path $ssmPath")

            val ssmEnvLoader = SsmEnvLoader(this, ssm)
            loaders.add(ssmEnvLoader)
        }

        return MultipleEnvLoaders(loaders)
    }
}
