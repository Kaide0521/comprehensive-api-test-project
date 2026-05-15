package com.example.assettest.shipment.dto;

import com.example.assettest.shipment.enums.CarrierType;
import com.example.assettest.shipment.enums.ShipmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body for searching shipments.
 */
@Data
@Schema(description = "Shipment search request")
public class ShipmentSearchRequest {
    @NotBlank
    @Schema(description = "Tenant id; must match authenticated tenant")
    private String tenantId;

    @Schema(description = "Shipment tracking numbers")
    private List<String> trackingNumbers;

    @Schema(description = "Shipment statuses")
    private Set<ShipmentStatus> statuses;

    @Schema(description = "Carrier channels")
    private Set<CarrierType> carriers;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Created date lower bound")
    private LocalDate createdFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Created date upper bound")
    private LocalDate createdTo;

    @Schema(description = "Metadata label filters")
    private Map<String, String> labels;

    @Schema(description = "Whether canceled shipments should be included")
    private Boolean includeCanceled = false;

    @Min(1)
    @Schema(description = "Page number")
    private Integer pageNum = 1;

    @Min(1)
    @Max(200)
    @Schema(description = "Page size")
    private Integer pageSize = 50;
}
