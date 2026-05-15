package com.example.assettest.shipment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import lombok.Data;

/**
 * Shipment-specific coordinate model.
 */
@Data
public class ShipmentCoordinateDTO {
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @Schema(description = "Latitude coordinate")
    private BigDecimal latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @Schema(description = "Longitude coordinate")
    private BigDecimal longitude;
}
