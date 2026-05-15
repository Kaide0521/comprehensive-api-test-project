package com.example.assettest.shipment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Response-side package line.
 */
@Data
public class ShipmentPackageVO {
    @Schema(description = "Server package id", accessMode = Schema.AccessMode.READ_ONLY)
    private Long packageId;

    @Schema(description = "Client package reference")
    private String packageRef;

    @Schema(description = "Number of pieces in the package")
    private Integer pieces;

    @Schema(description = "Package weight in kilograms")
    private BigDecimal weightKg;

    @Schema(description = "Declared content category")
    private String contentCategory;
}
