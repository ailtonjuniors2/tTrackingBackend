package com.monitoramento.ttracking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public record TelemetryRequest(

        @NotBlank
        @Size(max = 50)
        String deviceId,

        @NotNull
        Boolean motionDetected,

        @NotNull
        Boolean doorOpen,

        @NotNull
        Boolean panicPressed,

        @NotNull
        @DecimalMin("-40.0")
        @DecimalMax("85.0")
        Double temperature,

        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        Double humidity,

        @DecimalMin("300.0")
        @DecimalMax("1100.0")
        Double pressure

) {}
