package com.example.assettest.shipment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Detailed shipment response.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Detailed shipment response")
public class ShipmentDetailVO extends ShipmentSummaryVO {
    @Schema(description = "Tenant id", accessMode = Schema.AccessMode.READ_ONLY)
    private String tenantId;

    @Schema(description = "External order number")
    private String externalOrderNo;

    @Schema(description = "Recipient contact and address")
    private ShipmentRecipientDTO recipient;

    @Schema(description = "Package lines")
    private List<ShipmentPackageVO> packages;

    @Schema(description = "Expected delivery time window")
    private DeliveryWindowDTO deliveryWindow;

    @Schema(description = "Tracking checkpoints")
    private List<ShipmentCheckpointVO> checkpoints;

    @Schema(description = "Carrier extension attributes")
    private Map<String, Object> attributes;
}
