package ca.nexapp.lambdas.contexts.env

import ca.nexapp.logging.Logging
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder

/**
 * Creates the default EnvLoader which fallbacks to SSM if not found in local environment variables
 */
class LambdaEnvLoaderFactory(
    private val ssmPath: String? = System.getenv("SSM_PATH"),
    private val ssm: AWSSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.defaultClient()
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
