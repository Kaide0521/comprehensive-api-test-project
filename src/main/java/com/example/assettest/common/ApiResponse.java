package com.example.assettest.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Alternate response wrapper used by legacy endpoints.
 */
@Data
@Schema(description = "Alternate API response envelope")
public class ApiResponse<T> {
    @Schema(description = "Whether the request was processed successfully")
    private boolean success;

    @Schema(description = "Payload value")
    private T payload;

    @Schema(description = "Error detail when success is false")
    private ErrorResponse error;

    public static <T> ApiResponse<T> ok(T payload) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setPayload(payload);
        return response;
    }
}
