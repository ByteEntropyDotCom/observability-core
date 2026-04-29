package com.byteentropy.observability_core.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    /**
     * In a 99.99% system, this method would integrate with:
     * - Slack Webhooks
     * - PagerDuty API
     * - AWS SNS (Email/SMS)
     */
    public void raiseAlert(String title, String message, AlertLevel level) {
        log.error("🚨 [SYSTEM ALERT - {}] {}: {}", level, title, message);
        // Add logic here to push to external notification providers
    }

    public enum AlertLevel {
        INFO, WARNING, CRITICAL
    }
}