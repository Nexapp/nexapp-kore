## nexapp-tracing
```kotlin
return tracer.trace("Trace Name") { trace ->
    trace.setTag("id", "1234")
    trace.setMetadata("message", "Hello There")

    doingMyStuff()  // will be the output, if it throws, the trace will show up as in error
}
```
**Note** that for now traces are NOT thread-friendly and should be only consumed once

### Slf4JTracer
Traces will be logged to Slf4J by default

### SentryTracer
Traces will show up as breadcrumbs in sentry if `SENTRY_DSN` is in your env-loader

### XRayTracer
Traces will be put in AWS XRay if `XRAY_TRACING_ENABLED` is `true` in your env-loader
