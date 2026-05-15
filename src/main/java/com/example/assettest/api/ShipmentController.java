package com.example.assettest.api;

import com.example.assettest.annotations.RequiresTenant;
import com.example.assettest.common.ApiResponse;
import com.example.assettest.common.PageResult;
import com.example.assettest.common.Result;
import com.example.assettest.service.ShipmentService;
import com.example.assettest.shipment.dto.CreateShipmentRequest;
import com.example.assettest.shipment.dto.ShipmentDetailVO;
import com.example.assettest.shipment.dto.ShipmentSearchRequest;
import com.example.assettest.shipment.dto.ShipmentSummaryVO;
import com.example.assettest.shipment.enums.ShipmentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Shipment API fixture with its own DTO family, separate from order models.
 */
@RestController
@RequestMapping(value = "/api/v1/shipments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Shipment API", description = "Shipment creation, search, and tracking APIs")
@RequiresTenant("X-Warehouse-Tenant")
public class ShipmentController {
    private static final String TENANT_HEADER = "X-Warehouse-Tenant";

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * Search shipments with independent DTOs and page wrapper response.
     */
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search shipments", description = "Searches shipment rows by tenant, status, carrier, date, and labels.")
    @PreAuthorize("isAuthenticated()")
    public Result<PageResult<ShipmentSummaryVO>> searchShipments(
        @Valid @RequestBody ShipmentSearchRequest requestBody,
        @RequestHeader(TENANT_HEADER) String tenantId,
        @RequestParam(value = "cursor", required = false) String cursor) {
        requestBody.setTenantId(tenantId);
        return Result.success(shipmentService.searchShipments(requestBody, cursor));
    }

    /**
     * Create a shipment and return the real HTTP envelope.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create shipment", description = "Creates a shipment from recipient, package, and delivery window data.")
    @PreAuthorize("hasAuthority('shipment:create')")
    public ResponseEntity<Result<ShipmentDetailVO>> createShipment(
        @Valid @RequestBody CreateShipmentRequest requestBody,
        @RequestHeader(TENANT_HEADER) String tenantId,
        @RequestHeader(value = "X-Operator-Id", required = false) Long operatorId) {
        requestBody.setTenantId(tenantId);
        ShipmentDetailVO created = shipmentService.createShipment(requestBody, operatorId);
        return ResponseEntity.created(URI.create("/api/v1/shipments/" + created.getShipmentId()))
            .body(Result.success(created));
    }

    /**
     * Alternate response envelope with nested shipment detail.
     */
    @GetMapping("/{shipmentId}")
    @Operation(summary = "Get shipment detail", description = "Returns shipment detail using the alternate ApiResponse envelope.")
    @PreAuthorize("hasAuthority('shipment:read')")
    public ApiResponse<ShipmentDetailVO> getShipment(
        @PathVariable("shipmentId") @Min(1) Long shipmentId,
        @RequestHeader(TENANT_HEADER) String tenantId,
        @RequestParam(value = "includeCheckpoints", defaultValue = "true") boolean includeCheckpoints) {
        return shipmentService.getShipment(tenantId, shipmentId, includeCheckpoints);
    }

    /**
     * Patch endpoint with enum query parameter and Map response.
     */
    @PatchMapping("/{shipmentId}/status")
    @Operation(summary = "Change shipment status", description = "Changes shipment status and returns a map acknowledgement.")
    @PreAuthorize("hasAuthority('shipment:update')")
    public Result<Map<String, Object>> changeStatus(
        @PathVariable("shipmentId") @Min(1) Long shipmentId,
        @RequestHeader(TENANT_HEADER) String tenantId,
        @RequestParam("status") ShipmentStatus status,
        @RequestParam(value = "reason", required = false) String reason) {
        return Result.success(shipmentService.changeShipmentStatus(tenantId, shipmentId, status, reason));
    }

    /**
     * Form endpoint for x-www-form-urlencoded Map parsing.
     */
    @PutMapping(value = "/{shipmentId}/labels", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "Replace shipment labels", description = "Accepts form labels and returns the normalized label map.")
    public Result<Map<String, String>> replaceLabels(
        @PathVariable("shipmentId") @Min(1) Long shipmentId,
        @RequestHeader(TENANT_HEADER) String tenantId,
        @RequestParam Map<String, String> labels) {
        return Result.success(shipmentService.replaceLabels(tenantId, shipmentId, labels));
    }
}
