package ca.nexapp.logging

import org.slf4j.Logger

interface Logging {
    val logger: Logger
        get() = createKotlinLogger(javaClass)
}
