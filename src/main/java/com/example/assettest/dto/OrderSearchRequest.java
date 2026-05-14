package com.example.assettest.dto;

import com.example.assettest.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request body for searching orders.
 */
@Data
@Schema(description = "Order search request")
public class OrderSearchRequest extends BaseDTO {
    @Schema(description = "Tenant id; must match authenticated tenant")
    @NotBlank
    private String tenantId;

    @Schema(description = "Customer user id")
    private Long userId;

    @Schema(description = "Order status filter")
    private OrderStatus status;

    @Schema(description = "Allowed order id list")
    private List<Long> orderIds;

    @Schema(description = "Tag set used by advanced order filters")
    private Set<String> tags;

    @Schema(description = "Two-dimensional risk score matrix")
    private Integer[][] riskScoreMatrix;

    @Valid
    @Schema(description = "Shipping address filter")
    private AddressDTO address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Start date")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "End date")
    private LocalDate endDate;

    @Min(1)
    @Schema(description = "Page number")
    private Integer pageNum = 1;

    @Min(1)
    @Max(100)
    @Schema(description = "Page size")
    private Integer pageSize = 20;
}
