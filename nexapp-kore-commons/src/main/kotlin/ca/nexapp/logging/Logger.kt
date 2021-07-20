package ca.nexapp.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import kotlin.reflect.full.companionObject

// code inspired from Apache Log4J2 Kotlin API
// https://github.com/apache/logging-log4j-kotlin
fun <T : Any> createKotlinLogger(ofClass: Class<T>): Logger {
    val unwrapped = unwrapCompanionClass(ofClass)
    return LoggerFactory.getLogger(unwrapped)
}

private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return if (ofClass.enclosingClass?.kotlin?.companionObject?.java == ofClass) {
        ofClass.enclosingClass
    } else {
        ofClass
    }
}

fun Logger.log(level: Level, message: String) {
    when (level) {
        Level.TRACE -> trace(message)
        Level.DEBUG -> debug(message)
        Level.INFO -> info(message)
        Level.WARN -> warn(message)
        Level.ERROR -> error(message)
    }
}

fun Logger.log(level: Level, cause: Throwable, message: String? = null) {
    val exceptionMessage = when (message) {
        null -> cause.message ?: ""
        else -> message
    }

    when (level) {
        Level.TRACE -> trace(exceptionMessage, cause)
        Level.DEBUG -> debug(exceptionMessage, cause)
        Level.INFO -> info(exceptionMessage, cause)
        Level.WARN -> warn(exceptionMessage, cause)
        Level.ERROR -> error(exceptionMessage, cause)
    }
}
