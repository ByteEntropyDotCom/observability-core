package com.byteentropy.observability_core.alert;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HealthAlertHandler {
    private final AlertService alertService;

    public HealthAlertHandler(AlertService alertService) {
        this.alertService = alertService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void notifyStartup() {
        alertService.raiseAlert(
            "Observability Engine Online", 
            "All telemetry bridges (Tracing, Metrics, Logs) are active.", 
            AlertService.AlertLevel.INFO
        );
    }
}