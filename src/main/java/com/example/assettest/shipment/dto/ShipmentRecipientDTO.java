package com.example.assettest.shipment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * Shipment recipient contact and delivery address.
 */
@Data
public class ShipmentRecipientDTO {
    @NotBlank
    @Schema(description = "Recipient full name")
    private String name;

    @Pattern(regexp = "^1[0-9]{10}$")
    @Schema(description = "Recipient mobile phone")
    private String mobile;

    @Email
    @Schema(description = "Recipient email address")
    private String email;

    @JsonProperty("delivery_address")
    @Schema(description = "Recipient delivery address")
    private String deliveryAddress;

    @Valid
    @Schema(description = "Recipient delivery coordinate")
    private ShipmentCoordinateDTO coordinate;

    @JsonIgnore
    @Schema(description = "Internal recipient risk note")
    private String internalRiskNote;
}
