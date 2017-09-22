package io.opentracing.extensions.faultinjection;

import io.opentracing.ActiveSpan;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;

import java.util.Map;

public class CowardTracer implements FaultInjector, Tracer {

    private Tracer tracer;

    public CowardTracer(Tracer tracer) {

        this.tracer = tracer;
    }

    @Override
    public void injectFault(SpanContext spanContext, Format<TextMap> format, TextMap carrier, String targetService, String faultType) {
        carrier.put(FaultFields.FaultType, faultType);
        carrier.put(FaultFields.Target, targetService);
        tracer.inject(spanContext, format, carrier);
    }

    @Override
    public String extractFault(Format<TextMap> format, TextMap carrier) {
        tracer.extract(format, carrier);
        for (Map.Entry<String, String> entry : carrier) {
            if (FaultFields.FaultType.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }

        return FaultFields.NoFault;
    }

    @Override
    public Tracer.SpanBuilder buildSpan(String operationName) {
        return tracer.buildSpan(operationName);
    }

    @Override
    public <C> void inject(SpanContext spanContext, Format<C> format, C carrier) {
        tracer.inject(spanContext, format, carrier);
    }

    @Override
    public <C> SpanContext extract(Format<C> format, C carrier) {
        return tracer.extract(format, carrier);
    }

    @Override
    public ActiveSpan activeSpan() {
        return tracer.activeSpan();
    }

    @Override
    public ActiveSpan makeActive(Span span) {
        return tracer.makeActive(span);
    }
}
