package com.monitoramento.ttracking.repository;

import com.monitoramento.ttracking.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SensorReadingRepository
        extends JpaRepository<SensorReading, Long> {

    Optional<SensorReading>
    findFirstByDeviceIdOrderByReceivedAtDesc(String deviceId);

    List<SensorReading>
    findTop100ByDeviceIdOrderByReceivedAtDesc(String deviceId);
}

