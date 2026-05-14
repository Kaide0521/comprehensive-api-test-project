package com.example.assettest.dto;

import com.example.assettest.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * Detailed order response.
 */
@Data
@Schema(description = "Detailed order response")
public class OrderDetailVO extends BaseDTO {
    @Schema(description = "Order id")
    private Long orderId;

    @Schema(description = "Tenant id")
    private String tenantId;

    @Schema(description = "Order owner user id")
    private Long ownerUserId;

    @Schema(description = "Order status")
    private OrderStatus status;

    @Schema(description = "Total amount")
    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Payment time")
    private LocalDateTime paidAt;

    @Schema(description = "Customer profile")
    private CustomerDTO customer;

    @Schema(description = "Order item list")
    private List<OrderItemDTO> items;

    @Schema(description = "Payment information")
    private PaymentInfoDTO payment;

    @Schema(description = "Extension attributes")
    private Map<String, Object> attributes;

    @Schema(description = "Audit metadata")
    private AuditMetadata audit;
}
