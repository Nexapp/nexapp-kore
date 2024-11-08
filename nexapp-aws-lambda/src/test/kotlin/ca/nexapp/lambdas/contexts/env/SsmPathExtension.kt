package ca.nexapp.lambdas.contexts.env

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import software.amazon.awssdk.services.ssm.SsmClient
import software.amazon.awssdk.services.ssm.model.DeleteParametersRequest
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest
import software.amazon.awssdk.services.ssm.model.ParameterType
import software.amazon.awssdk.services.ssm.model.PutParameterRequest
import java.util.UUID

class SsmPathExtension : AfterAllCallback {

    val ssm: SsmClient = SsmClient.create()
    val ssmPath by lazy { "/lambda4j-test-${UUID.randomUUID()}" }

    fun add(name: String, value: String, encrypted: Boolean = false) {
        PutParameterRequest
            .builder()
            .name("$ssmPath/$name")
            .value(value)
            .type(if (encrypted) ParameterType.SECURE_STRING else ParameterType.STRING)
            .build()
            .also { ssm.putParameter(it) }
    }

    override fun afterAll(ctx: ExtensionContext?) {
        val parameters = GetParametersByPathRequest
            .builder()
            .path(ssmPath)
            .build()
            .let { ssm.getParametersByPath(it) }
            .parameters()
            .map { p -> p.name() }

        if (parameters.isEmpty()) {
            return
        }

        ssm.deleteParameters(DeleteParametersRequest.builder().names(parameters).build())
    }
}
