package com.example.assettest.service;

import com.example.assettest.common.ApiResponse;
import com.example.assettest.common.PageResult;
import com.example.assettest.common.Result;
import com.example.assettest.dto.AuditMetadata;
import com.example.assettest.dto.OrderDetailVO;
import com.example.assettest.dto.OrderSearchRequest;
import com.example.assettest.dto.OrderSummaryVO;
import com.example.assettest.entity.OrderEntity;
import com.example.assettest.mapper.OrderMapper;
import com.example.assettest.mapper.OrderStructMapper;
import com.example.assettest.security.CurrentUser;
import com.example.assettest.security.TenantAccessEvaluator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Service layer intentionally returns DTOs through wrappers, maps, Object, and
 * Optional so type resolution can infer concrete response schemas from calls.
 */
@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final TenantAccessEvaluator tenantAccessEvaluator;

    public OrderService(OrderMapper orderMapper, TenantAccessEvaluator tenantAccessEvaluator) {
        this.orderMapper = orderMapper;
        this.tenantAccessEvaluator = tenantAccessEvaluator;
    }

    /**
     * Returns a concrete detail DTO even when the controller only sees a
     * response wrapper.
     */
    public OrderDetailVO getOrderDetail(String tenantId, Long orderId, CurrentUser user) {
        OrderEntity entity = orderMapper.selectOrderEntity(tenantId, orderId);
        OrderDetailVO detail = OrderStructMapper.INSTANCE.toDetail(entity);
        detail.setAttributes(orderMapper.selectOrderAttributes(orderId));
        AuditMetadata audit = new AuditMetadata();
        audit.setOperatorUserId(user.getId());
        audit.setReason("read order detail");
        detail.setAudit(audit);
        if (!tenantAccessEvaluator.canReadOrder(user, tenantId, orderId, detail)) {
            throw new SecurityException("owner check failed");
        }
        return detail;
    }

    public PageResult<OrderSummaryVO> searchOrders(OrderSearchRequest request, CurrentUser user) {
        tenantAccessEvaluator.assertTenant(request.getTenantId(), user);
        List<OrderSummaryVO> rows = orderMapper.searchOrders(request).stream()
            .map(OrderStructMapper.INSTANCE::toSummary)
            .collect(Collectors.toList());
        PageResult<OrderSummaryVO> page = new PageResult<>();
        page.setPageNum(request.getPageNum());
        page.setPageSize(request.getPageSize());
        page.setTotal(rows.size());
        page.setRecords(rows);
        return page;
    }

    /**
     * The declared return type is Object, but the actual response is
     * ApiResponse<OrderDetailVO>.
     */
    public Object buildDynamicOrderResponse(String tenantId, Long orderId, CurrentUser user) {
        OrderDetailVO detail = getOrderDetail(tenantId, orderId, user);
        return ApiResponse.ok(detail);
    }

    /**
     * Map response whose values are derived from an OrderDetailVO.
     */
    public Map<String, Object> buildOrderMap(String tenantId, Long orderId, CurrentUser user) {
        OrderDetailVO detail = getOrderDetail(tenantId, orderId, user);
        Map<String, Object> map = new HashMap<>();
        map.put("order", detail);
        map.put("audit", detail.getAudit());
        map.put("tenantId", tenantId);
        return map;
    }

    public Optional<OrderDetailVO> findOptionalDetail(String tenantId, Long orderId, CurrentUser user) {
        return Optional.ofNullable(getOrderDetail(tenantId, orderId, user));
    }

    public Result<OrderDetailVO> copyAndWrap(OrderEntity entity) {
        OrderDetailVO vo = new OrderDetailVO();
        BeanUtils.copyProperties(entity, vo);
        return Result.success(vo);
    }
}
