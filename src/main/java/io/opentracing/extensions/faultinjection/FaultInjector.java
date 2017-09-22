package io.opentracing.extensions.faultinjection;

import io.opentracing.SpanContext;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;

public interface FaultInjector {
    void injectFault(SpanContext spanContext, Format<TextMap> format, TextMap carrier,
                     String targetService, String faultType);

    String extractFault(Format<TextMap> format, TextMap carrier);
}

