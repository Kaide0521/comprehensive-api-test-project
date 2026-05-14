package com.example.assettest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * Shipping address. Contains personal and location data for sensitivity tests.
 */
@Data
public class AddressDTO {
    @Schema(description = "Recipient full name")
    @NotBlank
    private String recipientName;

    @Schema(description = "Recipient mobile phone number")
    @Pattern(regexp = "^1[0-9]{10}$")
    private String phone;

    @Schema(description = "Street address")
    @JsonProperty("street_address")
    private String streetAddress;

    @Schema(description = "Postal code")
    private String postalCode;

    @Valid
    @Schema(description = "Geographic coordinate")
    private GeoCoordinate coordinate;
}
