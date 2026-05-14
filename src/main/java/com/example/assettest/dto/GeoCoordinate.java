package com.example.assettest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Customer delivery coordinate.
 */
@Data
public class GeoCoordinate {
    @Schema(description = "Latitude coordinate")
    private BigDecimal latitude;

    @Schema(description = "Longitude coordinate")
    private BigDecimal longitude;
}
