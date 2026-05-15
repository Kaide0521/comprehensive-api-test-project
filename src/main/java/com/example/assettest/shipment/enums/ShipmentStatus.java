package com.example.assettest.shipment.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Shipment lifecycle status.
 */
@Schema(description = "Shipment lifecycle status")
public enum ShipmentStatus {
    DRAFT,
    CREATED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    CANCELED
}
