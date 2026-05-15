package com.example.assettest.shipment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Map;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request-side package line for shipment creation.
 */
@Data
public class ShipmentPackageRequest {
    @NotBlank
    @Schema(description = "Client package reference")
    private String packageRef;

    @Min(1)
    @Schema(description = "Number of pieces in the package")
    private Integer pieces;

    @DecimalMin("0.01")
    @Schema(description = "Package weight in kilograms")
    private BigDecimal weightKg;

    @Schema(description = "Declared content category")
    private String contentCategory;

    @Schema(description = "Package-level metadata")
    private Map<String, String> metadata;
}
