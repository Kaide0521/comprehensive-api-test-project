package com.example.assettest.dto;

import com.example.assettest.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Payment details for an order.
 */
@Data
public class PaymentInfoDTO {
    @Schema(description = "Payment method")
    private PaymentMethod method;

    @Schema(description = "Masked bank card number")
    private String bankCardNo;

    @Schema(description = "Payment account number")
    private String accountNo;

    @Schema(description = "Amount paid")
    private BigDecimal amount;
}
