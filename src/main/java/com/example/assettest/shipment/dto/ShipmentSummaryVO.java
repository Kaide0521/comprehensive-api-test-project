package com.example.assettest.shipment.dto;

import com.example.assettest.shipment.enums.CarrierType;
import com.example.assettest.shipment.enums.ShipmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Compact shipment row for page responses.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShipmentSummaryVO extends ShipmentAuditFields {
    @Schema(description = "Shipment id", accessMode = Schema.AccessMode.READ_ONLY)
    private Long shipmentId;

    @Schema(description = "Tracking number", accessMode = Schema.AccessMode.READ_ONLY)
    private String trackingNo;

    @Schema(description = "Shipment status")
    private ShipmentStatus status;

    @Schema(description = "Carrier channel")
    private CarrierType carrier;

    @Schema(description = "Recipient display name")
    private String recipientName;

    @Schema(description = "Package count")
    private Integer packageCount;

    @Schema(description = "Total shipment weight in kilograms")
    private BigDecimal totalWeightKg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Latest tracking update time")
    private LocalDateTime latestEventTime;
}
