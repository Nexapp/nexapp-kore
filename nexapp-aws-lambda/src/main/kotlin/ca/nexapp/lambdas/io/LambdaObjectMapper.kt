package ca.nexapp.lambdas.io

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val INSTANCE = jacksonObjectMapper()
    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

/**
 * Jackson object mapper configured for Kotlin and missing/empty fields
 */
class LambdaObjectMapper : ObjectMapper(INSTANCE)
