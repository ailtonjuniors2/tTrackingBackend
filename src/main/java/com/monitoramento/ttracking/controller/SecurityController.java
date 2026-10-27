package com.monitoramento.ttracking.controller;


import com.monitoramento.ttracking.model.SecurityEvent;
import com.monitoramento.ttracking.repository.SecurityEventRepository;
import com.monitoramento.ttracking.service.EventDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final EventDetectionService eventDetectionService;
    private final SecurityEventRepository eventRepository;

    public SecurityController(
            EventDetectionService eventDetectionService,
            SecurityEventRepository eventRepository) {

        this.eventDetectionService = eventDetectionService;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/{deviceId}/arm")
    public ResponseEntity<Map<String, Object>> arm(
            @PathVariable String deviceId) {

        eventDetectionService.setArmed(deviceId, true);

        return ResponseEntity.ok(Map.of(
                "deviceId", deviceId,
                "armed", true,
                "message", "Sistema armado."
        ));
    }

    @PostMapping("/{deviceId}/disarm")
    public ResponseEntity<Map<String, Object>> disarm(
            @PathVariable String deviceId) {

        eventDetectionService.setArmed(deviceId, false);

        return ResponseEntity.ok(Map.of(
                "deviceId", deviceId,
                "armed", false,
                "message", "Sistema desarmado."
        ));
    }

    @GetMapping("/{deviceId}/status")
    public ResponseEntity<Map<String, Object>> status(
            @PathVariable String deviceId) {

        return ResponseEntity.ok(Map.of(
                "deviceId", deviceId,
                "armed", eventDetectionService.isArmed(deviceId)
        ));
    }

    @GetMapping("/{deviceId}/events")
    public ResponseEntity<List<SecurityEvent>> events(
            @PathVariable String deviceId) {

        return ResponseEntity.ok(
                eventRepository
                        .findTop100ByDeviceIdOrderByOccurredAtDesc(deviceId)
        );
    }
}
