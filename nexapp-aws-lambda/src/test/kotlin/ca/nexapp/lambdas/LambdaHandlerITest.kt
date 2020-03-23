package ca.nexapp.lambdas

import assertk.assertThat
import assertk.assertions.isEqualTo
import ca.nexapp.lambdas.contexts.LambdaContext
import com.amazonaws.services.lambda.runtime.Context
import io.mockk.mockk
import org.junit.jupiter.api.Test

class EchoLambdaHandler(lambdaContext: LambdaContext) : LambdaHandler<String, String>(lambdaContext) {
    override fun handle(input: String, context: Context): String {
        return "Echo: $input"
    }
}

class LambdaHandlerITest {

    private val runtimeContext = mockk<Context>(relaxed = true)

    @Test
    fun `should run lambda`() {
        val toSend = "hello world"

        val output = EchoLambdaHandler(LambdaContext())
            .handleRequest(toSend, runtimeContext)

        assertThat(output).isEqualTo("Echo: hello world")
    }
}
