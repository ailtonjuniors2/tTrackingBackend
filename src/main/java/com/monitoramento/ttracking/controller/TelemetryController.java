package com.monitoramento.ttracking.controller;

import com.monitoramento.ttracking.dto.TelemetryRequest;
import com.monitoramento.ttracking.model.SensorReading;
import com.monitoramento.ttracking.service.TelemetryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService service;

    public TelemetryController(TelemetryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> receive(
            @Valid @RequestBody TelemetryRequest request) {

        SensorReading saved = service.receive(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Telemetria recebida e salva",
                        "id", saved.getId(),
                        "deviceId", saved.getDeviceId(),
                        "receivedAt", saved.getReceivedAt().toString()
                ));
    }

    @GetMapping("/latest/{deviceId}")
    public ResponseEntity<SensorReading> latest(
            @PathVariable String deviceId) {

        return service.latest(deviceId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<SensorReading>> history(
            @PathVariable String deviceId) {

        return ResponseEntity.ok(service.history(deviceId));
    }
}
