package com.example.assettest.entity;

import com.example.assettest.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Order persistence object returned by MyBatis mapper methods.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderEntity extends BaseEntity {
    private Long orderId;
    private String tenantId;
    private Long ownerUserId;
    private String customerName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime paidAt;
}
