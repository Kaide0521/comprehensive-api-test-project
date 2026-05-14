package com.example.assettest.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Standard business response envelope.
 *
 * @param <T> actual response data type
 */
@Data
@Schema(description = "Standard business response envelope")
public class Result<T> {
    @Schema(description = "Business status code, 0 means success")
    private int code;

    @Schema(description = "Human-readable result message")
    private String message;

    @Schema(description = "Actual response payload")
    private T data;

    @Schema(description = "Trace id for request diagnostics")
    private String traceId;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failure(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
