package com.monitoramento.ttracking.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 51)
    private String deviceId;

    @Column(nullable = false)
    private boolean motionDetected;

    @Column(nullable = false)
    private boolean doorOpen;

    @Column(nullable = false)
    private boolean panicPressed;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double humidity;

    private Double pressure;

    @Column(nullable = false)
    private Instant receivedAt;

    protected SensorReading() {
    }

    public SensorReading(
            String deviceId,
            boolean motionDetected,
            boolean doorOpen,
            boolean panicPressed,
            double temperature,
            double humidity,
            Double pressure) {

        this.deviceId = deviceId;
        this.motionDetected = motionDetected;
        this.doorOpen = doorOpen;
        this.panicPressed = panicPressed;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.receivedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public boolean isPanicPressed() {
        return panicPressed;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}

