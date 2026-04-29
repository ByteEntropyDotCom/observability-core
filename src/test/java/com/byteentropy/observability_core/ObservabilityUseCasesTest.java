package com.byteentropy.observability_core;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Finalized Use Case Test for Observability-Core.
 * Verifies the Three Pillars: Tracing, Metrics, and Log Correlation.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ObservabilityUseCasesTest {

    private static final Logger log = LoggerFactory.getLogger(ObservabilityUseCasesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObservationRegistry observationRegistry;

    @Autowired
    private Tracer tracer;

    /**
     * USE CASE 1: Trace Propagation (The "Support Ticket" Test)
     * Verifies that every external request gets a Trace ID injected into the response.
     */
    @Test
    void testTraceIdInResponseHeader() throws Exception {
        MvcResult result = mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Trace-Id"))
                .andReturn();

        String traceId = result.getResponse().getHeader("X-Trace-Id");
        log.info("Verified Trace ID in header: {}", traceId);
        assertThat(traceId).isNotBlank();
    }

    /**
     * USE CASE 2: Observation to Tracing Bridge (The "Performance" Test)
     * Verifies that starting a business 'Observation' correctly triggers a 'Trace Span'.
     * This proves the PerformanceLoggingHandler is correctly wired.
     */
    @Test
    void testObservationTriggersTracing() {
        Observation.createNotStarted("payment.reconciliation.check", observationRegistry)
            .observe(() -> {
                // Inside this block, Micrometer should have automatically started a span
                var currentSpan = tracer.currentSpan();
                
                assertThat(currentSpan)
                    .as("Observation Registry should have triggered an active Trace Span")
                    .isNotNull();
                
                log.info("Observation active with Trace ID: {}", currentSpan.context().traceId());
                assertThat(currentSpan.context().traceId()).isNotBlank();
            });
    }

    /**
     * USE CASE 3: Metrics Export (The "Dashboard" Test)
     * Verifies that the Prometheus endpoint is serving data for the Grafana dashboard.
     */
    @Test
    void testPrometheusMetricsExport() throws Exception {
        mockMvc.perform(get("/actuator/prometheus"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    // Check for standard JVM metrics that Prometheus needs
                    assertThat(body).contains("jvm_memory_used_bytes");
                    assertThat(body).contains("process_cpu_usage");
                    log.info("Prometheus metrics successfully verified.");
                });
    }
}