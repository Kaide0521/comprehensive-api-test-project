package com.example.assettest.shipment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Shipment tracking checkpoint.
 */
@Data
public class ShipmentCheckpointVO {
    @Schema(description = "Checkpoint city")
    private String city;

    @Schema(description = "Checkpoint operation")
    private String action;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Checkpoint event time")
    private LocalDateTime eventTime;
}
