package ca.nexapp.lambdas.contexts.env

import software.amazon.awssdk.services.ssm.SsmClient
import software.amazon.awssdk.services.ssm.model.GetParameterRequest
import software.amazon.awssdk.services.ssm.model.SsmException

/**
 * Loads and decrypts variables from SSM Parameter Store Path
 * Make sure that your environment has the required IAM policies to access requested variables
 *
 * @param ssmPath prefix to the SSM configuration (ex.: "myproject/staging")
 * @param ssm SSM client
 */
class SsmEnvLoader(
    private val ssmPath: String,
    private val ssm: SsmClient
) : LambdaEnvLoader {

    override fun load(env: String) = GetParameterRequest
        .builder()
        .name("$ssmPath/$env")
        .withDecryption(true)
        .build()
        .let { load(env, it) }

    private fun load(env: String, request: GetParameterRequest): String {
        return try {
            ssm.getParameter(request).parameter().value()
        } catch (error: SsmException) {
            throw CouldNotLoadEnvException(env, error)
        }
    }
}
