package com.monitoramento.ttracking.service;


import com.monitoramento.ttracking.model.SecurityEvent;
import com.monitoramento.ttracking.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventDetectionService {

    private static final double TEMPERATURE_LIMIT = 45.0;

    private final SecurityEventRepository repository;

    // Estado temporário de cada dispositivo.
    private final Map<String, DeviceState> states =
            new ConcurrentHashMap<>();

    public EventDetectionService(SecurityEventRepository repository) {
        this.repository = repository;
    }

    public void setArmed(String deviceId, boolean armed) {
        DeviceState state = getState(deviceId);
        state.armed = armed;
    }

    public boolean isArmed(String deviceId) {
        return getState(deviceId).armed;
    }

    public void process(
            String deviceId,
            boolean motionDetected,
            boolean doorOpen,
            boolean panicPressed,
            double temperature) {

        DeviceState state = getState(deviceId);

        // 1. Movimento: registra somente a transição para ativo.
        if (motionDetected && !state.motionActive) {
            createEvent(
                    deviceId,
                    "MOTION",
                    "INFO",
                    "Movimento detectado pelo sensor PIR."
            );
        }

        state.motionActive = motionDetected;

        // 2. Invasão: porta aberta enquanto o sistema está armado.
        boolean intrusion =
                state.armed && doorOpen;

        if (intrusion && !state.intrusionActive) {
            createEvent(
                    deviceId,
                    "INTRUSION",
                    "WARNING",
                    "Acesso aberto enquanto o sistema estava armado."
            );
        }

        state.intrusionActive = intrusion;

        // 3. Anomalia térmica.
        boolean thermalAnomaly =
                temperature >= TEMPERATURE_LIMIT;

        if (thermalAnomaly && !state.thermalActive) {
            createEvent(
                    deviceId,
                    "THERMAL_ANOMALY",
                    "WARNING",
                    "Temperatura acima do limite de "
                            + TEMPERATURE_LIMIT + " graus Celsius."
            );
        }

        state.thermalActive = thermalAnomaly;

        // 4. Botão de pânico.
        if (panicPressed && !state.panicActive) {
            createEvent(
                    deviceId,
                    "PANIC",
                    "CRITICAL",
                    "Botão de pânico acionado."
            );
        }

        state.panicActive = panicPressed;
    }

    private DeviceState getState(String deviceId) {
        return states.computeIfAbsent(
                deviceId,
                id -> new DeviceState()
        );
    }

    private void createEvent(
            String deviceId,
            String type,
            String severity,
            String description) {

        SecurityEvent event = new SecurityEvent(
                deviceId,
                type,
                severity,
                description
        );

        repository.save(event);

        System.out.println(
                "[" + severity + "] "
                        + type + " - " + deviceId
                        + ": " + description
        );
    }

    private static class DeviceState {

        private boolean armed = false;
        private boolean motionActive = false;
        private boolean intrusionActive = false;
        private boolean thermalActive = false;
        private boolean panicActive = false;
    }
}