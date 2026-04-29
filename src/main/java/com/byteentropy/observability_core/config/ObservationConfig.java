package com.byteentropy.observability_core.config;

import com.byteentropy.observability_core.handler.PerformanceLoggingHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy // Add this line
public class ObservationConfig {

    @Bean
    ObservationRegistry observationRegistry(PerformanceLoggingHandler performanceHandler) {
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig().observationHandler(performanceHandler);
        return registry;
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}