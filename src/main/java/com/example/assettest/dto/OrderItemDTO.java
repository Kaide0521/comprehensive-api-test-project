package com.example.assettest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Order item row.
 */
@Data
public class OrderItemDTO {
    @Schema(description = "SKU code")
    @NotBlank
    private String skuCode;

    @Schema(description = "Product name")
    private String productName;

    @Min(1)
    @Schema(description = "Purchased quantity")
    private Integer quantity;

    @Schema(description = "Unit price")
    private BigDecimal unitPrice;
}
