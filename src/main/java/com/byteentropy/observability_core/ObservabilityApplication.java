package com.byteentropy.observability_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Central Nervous System of the ByteEntropy ecosystem.
 * This application manages telemetry, metrics, and global monitoring.
 */
@SpringBootApplication
public class ObservabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservabilityApplication.class, args);
    }
}