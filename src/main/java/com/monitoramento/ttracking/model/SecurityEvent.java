package com.monitoramento.ttracking.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "security_events")
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String deviceId;

    @Column(nullable = false, length = 40)
    private String eventType;

    @Column(nullable = false, length = 20)
    private String severity;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private Instant occurredAt;

    protected SecurityEvent() {
    }

    public SecurityEvent(
            String deviceId,
            String eventType,
            String severity,
            String description) {

        this.deviceId = deviceId;
        this.eventType = eventType;
        this.severity = severity;
        this.description = description;
        this.occurredAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }
}
