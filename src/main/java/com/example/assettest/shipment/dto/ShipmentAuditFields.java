package com.example.assettest.shipment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Base shipment audit fields, independent from order DTO inheritance.
 */
@Data
public abstract class ShipmentAuditFields {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Shipment record creation time", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Shipment record last update time", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "User who last changed the shipment", accessMode = Schema.AccessMode.READ_ONLY)
    private String changedBy;
}
