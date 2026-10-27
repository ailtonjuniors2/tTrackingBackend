package com.monitoramento.ttracking.service;

import com.monitoramento.ttracking.dto.TelemetryRequest;
import com.monitoramento.ttracking.model.SensorReading;
import com.monitoramento.ttracking.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TelemetryService {

    private final SensorReadingRepository repository;
    private final EventDetectionService eventDetectionService;

    public TelemetryService(
            SensorReadingRepository repository,
            EventDetectionService eventDetectionService) {

        this.repository = repository;
        this.eventDetectionService = eventDetectionService;
    }

    @Transactional
    public SensorReading receive(TelemetryRequest request) {

        SensorReading reading = new SensorReading(
                request.deviceId(),
                request.motionDetected(),
                request.doorOpen(),
                request.panicPressed(),
                request.temperature(),
                request.humidity(),
                request.pressure()
        );

        SensorReading saved = repository.save(reading);

        eventDetectionService.process(
                request.deviceId(),
                request.motionDetected(),
                request.doorOpen(),
                request.panicPressed(),
                request.temperature()
        );

        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<SensorReading> latest(String deviceId) {
        return repository
                .findFirstByDeviceIdOrderByReceivedAtDesc(deviceId);
    }

    @Transactional(readOnly = true)
    public List<SensorReading> history(String deviceId) {
        return repository
                .findTop100ByDeviceIdOrderByReceivedAtDesc(deviceId);
    }
}