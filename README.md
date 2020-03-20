![CI](https://github.com/Nexapp/nexapp-kore/workflows/CI/badge.svg)

# Nexapp Kore
Nexapp Librairies for Kotlin

## Requirements
* Java 8
* Maven 3.5+
* GNU Make

## Getting Started
Use the makefile to do basic operations
```bash
$ make help
Nexapp Kore
> test
> lint
> coverage      - Generates Jacoco coverage report
```

## Projects
The libraries are divided throught multiple maven modules

### nexapp-tracing
A Kotlin tracing library
```kotlin
val pingResult = tracer.trace("Ping") { trace ->
    trace.setTag("ip", currentIp)
    
    ping(currentIp)
}
```
