package com.example.assettest.shipment.dto;

import com.example.assettest.shipment.enums.CarrierType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Request body for creating a shipment.
 */
@Data
@Schema(description = "Create shipment request")
public class CreateShipmentRequest {
    @NotBlank
    @Schema(description = "Tenant id; copied from request header", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String tenantId;

    @NotBlank
    @JsonProperty("external_order_no")
    @Schema(description = "Client-side external order number")
    private String externalOrderNo;

    @Schema(description = "Carrier channel")
    private CarrierType carrier;

    @Valid
    @Schema(description = "Recipient contact and address")
    private ShipmentRecipientDTO recipient;

    @Valid
    @NotEmpty
    @Schema(description = "Shipment package lines")
    private List<ShipmentPackageRequest> packages;

    @Valid
    @Schema(description = "Expected delivery time window")
    private DeliveryWindowDTO deliveryWindow;

    @DecimalMin("0.00")
    @Schema(description = "Insurance amount")
    private BigDecimal insuranceAmount;

    @Schema(description = "Whether the shipment contains fragile goods")
    private Boolean fragile;

    @Schema(description = "Free-form client metadata")
    private Map<String, String> metadata;
}
