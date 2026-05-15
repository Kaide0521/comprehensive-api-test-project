package com.example.assettest.shipment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Requested delivery time window.
 */
@Data
public class DeliveryWindowDTO {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Schema(description = "Delivery window start time")
    private OffsetDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @Schema(description = "Delivery window end time")
    private OffsetDateTime endAt;
}
