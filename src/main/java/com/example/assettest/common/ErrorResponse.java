package com.example.assettest.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error model returned by the global exception handler and ApiResponse.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response model")
public class ErrorResponse {
    @Schema(description = "Error code")
    private String errorCode;

    @Schema(description = "Error message")
    private String errorMessage;

    @Schema(description = "Trace id for diagnostics")
    private String traceId;
}
