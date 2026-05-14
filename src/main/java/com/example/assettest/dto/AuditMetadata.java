package com.example.assettest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Nested audit metadata included in selected responses.
 */
@Data
public class AuditMetadata {
    @Schema(description = "Operator user id")
    private Long operatorUserId;

    @Schema(description = "Operator IP address")
    private String operatorIp;

    @Schema(description = "Operation reason")
    private String reason;
}
