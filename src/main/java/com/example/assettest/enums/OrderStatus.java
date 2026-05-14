package com.example.assettest.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Order lifecycle status.
 */
@Schema(description = "Order lifecycle status")
public enum OrderStatus {
    CREATED,
    PAID,
    SHIPPED,
    COMPLETED,
    CANCELED
}
