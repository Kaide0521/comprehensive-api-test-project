package com.example.assettest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Customer profile used by order APIs.
 */
@Data
public class CustomerDTO extends BaseDTO {
    @Schema(description = "Customer user id")
    private Long userId;

    @Schema(description = "Tenant id to which the customer belongs")
    private String tenantId;

    @Schema(description = "Customer full name")
    @NotBlank
    private String fullName;

    @Schema(description = "Customer email address")
    @Email
    private String email;

    @Schema(description = "Masked identity card number")
    private String idCardNo;

    @JsonIgnore
    @Schema(description = "Internal password hash, never exposed")
    private String passwordHash;

    @Valid
    @Schema(description = "Known shipping addresses")
    private List<AddressDTO> addresses;
}
