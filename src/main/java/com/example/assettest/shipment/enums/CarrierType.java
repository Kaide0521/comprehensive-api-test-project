package com.example.assettest.shipment.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Shipment carrier channel.
 */
@Schema(description = "Shipment carrier channel")
public enum CarrierType {
    EXPRESS("express"),
    FREIGHT("freight"),
    SAME_DAY("same_day"),
    PICKUP("pickup");

    private final String code;

    CarrierType(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
