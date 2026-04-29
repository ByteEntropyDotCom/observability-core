package com.byteentropy.observability_core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Standard Spring Boot context load test.
 * Verifies that all observability beans (Tracer, MeterRegistry, ObservationHandlers)
 * are correctly wired and the application is ready for telemetry.
 */
@SpringBootTest
@ActiveProfiles("test")
class ObservabilityCoreApplicationTests {

    @Test
    void contextLoads() {
        // If this method executes, the Spring Context is healthy.
    }

}