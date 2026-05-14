package com.example.assettest.dto;

import com.example.assettest.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Compact order row for page responses.
 */
@Data
public class OrderSummaryVO {
    @Schema(description = "Order id")
    private Long orderId;

    @Schema(description = "Tenant id")
    private String tenantId;

    @Schema(description = "Customer display name")
    private String customerName;

    @Schema(description = "Order status")
    private OrderStatus status;

    @Schema(description = "Total amount")
    private BigDecimal totalAmount;
}
