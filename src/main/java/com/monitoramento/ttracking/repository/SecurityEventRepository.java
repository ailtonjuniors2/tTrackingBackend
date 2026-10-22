package com.monitoramento.ttracking.repository;

import com.monitoramento.ttracking.model.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityEventRepository
        extends JpaRepository<SecurityEvent, Long> {

    List<SecurityEvent>
    findTop100ByDeviceIdOrderByOccurredAtDesc(String deviceId);
}
