package com.example.assettest.service;

import com.example.assettest.common.ApiResponse;
import com.example.assettest.common.PageResult;
import com.example.assettest.shipment.dto.CreateShipmentRequest;
import com.example.assettest.shipment.dto.DeliveryWindowDTO;
import com.example.assettest.shipment.dto.ShipmentCheckpointVO;
import com.example.assettest.shipment.dto.ShipmentDetailVO;
import com.example.assettest.shipment.dto.ShipmentPackageRequest;
import com.example.assettest.shipment.dto.ShipmentPackageVO;
import com.example.assettest.shipment.dto.ShipmentRecipientDTO;
import com.example.assettest.shipment.dto.ShipmentSearchRequest;
import com.example.assettest.shipment.dto.ShipmentSummaryVO;
import com.example.assettest.shipment.enums.CarrierType;
import com.example.assettest.shipment.enums.ShipmentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Shipment fixture service that intentionally avoids reusing order DTOs.
 */
@Service
public class ShipmentService {
    public PageResult<ShipmentSummaryVO> searchShipments(ShipmentSearchRequest request, String cursor) {
        List<ShipmentSummaryVO> records = new ArrayList<>();
        records.add(sampleSummary(request.getTenantId(), 90001L));

        PageResult<ShipmentSummaryVO> page = new PageResult<>();
        page.setPageNum(request.getPageNum());
        page.setPageSize(request.getPageSize());
        page.setTotal(records.size());
        page.setRecords(records);
        return page;
    }

    public ShipmentDetailVO createShipment(CreateShipmentRequest request, Long operatorId) {
        ShipmentDetailVO detail = sampleDetail(request.getTenantId(), 90002L);
        detail.setExternalOrderNo(request.getExternalOrderNo());
        detail.setCarrier(request.getCarrier());
        detail.setRecipient(request.getRecipient());
        detail.setDeliveryWindow(request.getDeliveryWindow());
        detail.setPackages(toPackageViews(request.getPackages()));
        detail.setPackageCount(detail.getPackages().size());
        detail.setChangedBy(String.valueOf(operatorId));
        return detail;
    }

    public ApiResponse<ShipmentDetailVO> getShipment(String tenantId, Long shipmentId, boolean includeCheckpoints) {
        ShipmentDetailVO detail = sampleDetail(tenantId, shipmentId);
        if (!includeCheckpoints) {
            detail.setCheckpoints(new ArrayList<>());
        }
        return ApiResponse.ok(detail);
    }

    public Map<String, Object> changeShipmentStatus(String tenantId, Long shipmentId, ShipmentStatus status,
                                                    String reason) {
        Map<String, Object> result = new HashMap<>();
        result.put("tenantId", tenantId);
        result.put("shipmentId", shipmentId);
        result.put("status", status);
        result.put("reason", reason);
        result.put("changedAt", LocalDateTime.now());
        return result;
    }

    public Map<String, String> replaceLabels(String tenantId, Long shipmentId, Map<String, String> labels) {
        Map<String, String> result = new HashMap<>();
        result.put("tenantId", tenantId);
        result.put("shipmentId", String.valueOf(shipmentId));
        result.putAll(labels);
        return result;
    }

    private ShipmentSummaryVO sampleSummary(String tenantId, Long shipmentId) {
        ShipmentSummaryVO summary = new ShipmentSummaryVO();
        summary.setShipmentId(shipmentId);
        summary.setTrackingNo("TRK" + shipmentId);
        summary.setStatus(ShipmentStatus.IN_TRANSIT);
        summary.setCarrier(CarrierType.EXPRESS);
        summary.setRecipientName("Fixture Receiver");
        summary.setPackageCount(2);
        summary.setTotalWeightKg(new BigDecimal("3.50"));
        summary.setLatestEventTime(LocalDateTime.now());
        summary.setCreatedAt(LocalDateTime.now().minusDays(1));
        summary.setUpdatedAt(LocalDateTime.now());
        summary.setChangedBy("shipment-fixture");
        return summary;
    }

    private ShipmentDetailVO sampleDetail(String tenantId, Long shipmentId) {
        ShipmentDetailVO detail = new ShipmentDetailVO();
        ShipmentSummaryVO summary = sampleSummary(tenantId, shipmentId);
        detail.setShipmentId(summary.getShipmentId());
        detail.setTrackingNo(summary.getTrackingNo());
        detail.setStatus(summary.getStatus());
        detail.setCarrier(summary.getCarrier());
        detail.setRecipientName(summary.getRecipientName());
        detail.setPackageCount(summary.getPackageCount());
        detail.setTotalWeightKg(summary.getTotalWeightKg());
        detail.setLatestEventTime(summary.getLatestEventTime());
        detail.setCreatedAt(summary.getCreatedAt());
        detail.setUpdatedAt(summary.getUpdatedAt());
        detail.setChangedBy(summary.getChangedBy());
        detail.setTenantId(tenantId);
        detail.setExternalOrderNo("EXT-" + shipmentId);
        detail.setRecipient(sampleRecipient());
        detail.setDeliveryWindow(new DeliveryWindowDTO());
        detail.setPackages(samplePackages());
        detail.setCheckpoints(sampleCheckpoints());
        detail.setAttributes(sampleAttributes());
        return detail;
    }

    private ShipmentRecipientDTO sampleRecipient() {
        ShipmentRecipientDTO recipient = new ShipmentRecipientDTO();
        recipient.setName("Fixture Receiver");
        recipient.setMobile("13800000000");
        recipient.setEmail("receiver@example.com");
        recipient.setDeliveryAddress("100 Fixture Road");
        return recipient;
    }

    private List<ShipmentPackageVO> toPackageViews(List<ShipmentPackageRequest> packages) {
        List<ShipmentPackageVO> views = new ArrayList<>();
        if (packages == null) {
            return views;
        }
        long index = 1L;
        for (ShipmentPackageRequest item : packages) {
            ShipmentPackageVO view = new ShipmentPackageVO();
            view.setPackageId(index++);
            view.setPackageRef(item.getPackageRef());
            view.setPieces(item.getPieces());
            view.setWeightKg(item.getWeightKg());
            view.setContentCategory(item.getContentCategory());
            views.add(view);
        }
        return views;
    }

    private List<ShipmentPackageVO> samplePackages() {
        List<ShipmentPackageVO> packages = new ArrayList<>();
        ShipmentPackageVO item = new ShipmentPackageVO();
        item.setPackageId(1L);
        item.setPackageRef("PKG-1");
        item.setPieces(1);
        item.setWeightKg(new BigDecimal("1.75"));
        item.setContentCategory("general");
        packages.add(item);
        return packages;
    }

    private List<ShipmentCheckpointVO> sampleCheckpoints() {
        List<ShipmentCheckpointVO> checkpoints = new ArrayList<>();
        ShipmentCheckpointVO checkpoint = new ShipmentCheckpointVO();
        checkpoint.setCity("Shanghai");
        checkpoint.setAction("Departed sorting center");
        checkpoint.setEventTime(LocalDateTime.now());
        checkpoints.add(checkpoint);
        return checkpoints;
    }

    private Map<String, Object> sampleAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("temperatureControlled", Boolean.FALSE);
        attributes.put("source", "fixture");
        return attributes;
    }
}
