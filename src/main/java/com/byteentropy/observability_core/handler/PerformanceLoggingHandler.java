package com.byteentropy.observability_core.handler;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.Duration;

@Component
public class PerformanceLoggingHandler implements ObservationHandler<Observation.Context> {
    private static final Logger log = LoggerFactory.getLogger(PerformanceLoggingHandler.class);
    private static final String START_TIME = "start.time";

    @Override
    public void onStart(Observation.Context context) {
        context.put(START_TIME, Instant.now());
        log.info("🚀 Execution started: {}", context.getName());
    }

    @Override
    public void onStop(Observation.Context context) {
        Instant start = context.get(START_TIME);
        if (start != null) {
            long duration = Duration.between(start, Instant.now()).toMillis();
            log.info("✅ Execution finished: {} took {} ms", context.getName(), duration);
        }
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}