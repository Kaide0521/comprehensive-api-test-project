package com.example.assettest.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.assettest.annotations.RequiresOwner;
import com.example.assettest.annotations.RequiresTenant;
import com.example.assettest.common.ApiConstants;
import com.example.assettest.common.ApiResponse;
import com.example.assettest.common.PageResult;
import com.example.assettest.common.Result;
import com.example.assettest.dto.OrderDetailVO;
import com.example.assettest.dto.OrderSearchRequest;
import com.example.assettest.dto.OrderSummaryVO;
import com.example.assettest.security.CurrentUser;
import com.example.assettest.service.OrderService;
import com.example.assettest.util.JsonUtil;
import com.example.assettest.util.ParamUtil;
import com.example.assettest.util.RequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Order API fixture containing annotations, comments, dynamic request parsing,
 * security metadata, wrapper responses, Object responses, Map responses, and
 * servlet response writing.
 */
@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Order API", description = "Order query and management APIs")
@RequiresTenant(ApiConstants.HEADER_TENANT_ID)
public class OrderController {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    public OrderController(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    /**
     * Get one order by id.
     *
     * @param orderId order id from URL path
     * @param tenantId tenant id header
     * @param traceId optional trace id header
     * @param sessionId session cookie
     * @param includeAudit include audit metadata
     * @return order detail wrapped by Result
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "Get order detail", description = "Returns one order detail after tenant and owner checks.")
    @PreAuthorize("hasAuthority('order:read')")
    @RequiresPermissions("order:read")
    @RequiresOwner(resourceId = "#orderId")
    public ResponseEntity<Result<OrderDetailVO>> getOrderDetail(
        @Parameter(description = "Order id") @PathVariable("orderId") @Min(1) Long orderId,
        @RequestHeader(ApiConstants.HEADER_TENANT_ID) String tenantId,
        @RequestHeader(value = ApiConstants.HEADER_TRACE_ID, required = false) String traceId,
        @CookieValue(value = ApiConstants.COOKIE_SESSION, required = false) String sessionId,
        @RequestParam(value = "includeAudit", defaultValue = "false") Boolean includeAudit,
        HttpServletRequest request) {
        CurrentUser user = currentUser(request);
        OrderDetailVO detail = orderService.getOrderDetail(tenantId, orderId, user);
        Result<OrderDetailVO> result = Result.success(detail);
        result.setTraceId(traceId);
        return ResponseEntity.ok(result);
    }

    /**
     * Search orders with a JSON body and page wrapper response.
     *
     * @param requestBody search filters
     * @param tenantId tenant id header
     * @return page result of order summaries
     */
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search orders", description = "Searches orders by tenant, owner, status, date range, and address.")
    @PreAuthorize("@tenantAccessEvaluator.assertTenant(#tenantId, authentication.principal) == null")
    public Result<PageResult<OrderSummaryVO>> searchOrders(
        @Valid @RequestBody OrderSearchRequest requestBody,
        @RequestHeader(ApiConstants.HEADER_TENANT_ID) String tenantId,
        @RequestParam(value = "sort", defaultValue = "createdAt,desc") String sort,
        HttpServletRequest servletRequest) {
        requestBody.setTenantId(tenantId);
        CurrentUser user = currentUser(servletRequest);
        return Result.success(orderService.searchOrders(requestBody, user));
    }

    /**
     * Dynamic endpoint where request parameters and response schema are inferred
     * from request utilities and service return statements.
     */
    @PostMapping(value = "/dynamic", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Dynamic order endpoint", description = "Parses request body and returns Object that is actually ApiResponse<OrderDetailVO>.")
    public Object dynamicOrder(HttpServletRequest request) throws IOException {
        String tenantId = RequestUtil.requiredTenant(request);
        Long userId = RequestUtil.requiredUserId(request);
        String channel = RequestUtil.optionalChannel(request);
        String source = ParamUtil.firstNonBlank(request.getParameter("source"), channel);
        String sessionToken = RequestUtil.sessionToken(request);
        String bodyType = request.getHeader("X-Body-Type");
        OrderSearchRequest search = JsonUtil.readBody(request, OrderSearchRequest.class);
        JSONObject raw = JSON.parseObject(objectMapper.writeValueAsString(search));
        Long orderId = raw.getLong("orderId");
        CurrentUser user = new CurrentUser(userId, tenantId, "dynamic-user", new String[] {"USER"});
        return orderService.buildDynamicOrderResponse(tenantId, orderId, user);
    }

    /**
     * ResponseEntity with Map body; service map contains an OrderDetailVO.
     */
    @GetMapping("/{orderId}/map")
    @Operation(summary = "Get order map", description = "Returns a Map containing order and audit payloads.")
    public ResponseEntity<Map<String, Object>> getOrderMap(
        @PathVariable Long orderId,
        @RequestHeader(ApiConstants.HEADER_TENANT_ID) String tenantId,
        HttpServletRequest request) {
        return ResponseEntity.ok(orderService.buildOrderMap(tenantId, orderId, currentUser(request)));
    }

    /**
     * HttpEntity body with an alternate response wrapper.
     */
    @PutMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update order", description = "Accepts HttpEntity<OrderSearchRequest> and returns ApiResponse<OrderDetailVO>.")
    public ResponseEntity<ApiResponse<OrderDetailVO>> updateOrder(
        @PathVariable Long orderId,
        HttpEntity<OrderSearchRequest> entity,
        @RequestHeader(ApiConstants.HEADER_TENANT_ID) String tenantId,
        HttpServletRequest request) {
        OrderDetailVO detail = orderService.getOrderDetail(tenantId, orderId, currentUser(request));
        return ResponseEntity.ok(ApiResponse.ok(detail));
    }

    /**
     * Optional response wrapper.
     */
    @GetMapping("/{orderId}/optional")
    public Result<Optional<OrderDetailVO>> getOptional(
        @PathVariable Long orderId,
        @RequestHeader(ApiConstants.HEADER_TENANT_ID) String tenantId,
        HttpServletRequest request) {
        return Result.success(orderService.findOptionalDetail(tenantId, orderId, currentUser(request)));
    }

    /**
     * Manual servlet response writer that emits JSON text.
     */
    @GetMapping("/{orderId}/export")
    @Operation(summary = "Export order", description = "Writes JSON directly to HttpServletResponse.")
    public void exportOrder(
        @PathVariable Long orderId,
        @RequestHeader(ApiConstants.HEADER_TENANT_ID) String tenantId,
        HttpServletRequest request,
        HttpServletResponse response) throws IOException {
        OrderDetailVO detail = orderService.getOrderDetail(tenantId, orderId, currentUser(request));
        response.setContentType(ApiConstants.MEDIA_JSON);
        response.getWriter().write(JSON.toJSONString(ApiResponse.ok(detail)));
    }

    /**
     * View endpoint for ModelAndView response detection.
     */
    @GetMapping("/view")
    public ModelAndView orderView(@RequestParam("orderId") Long orderId) {
        ModelAndView view = new ModelAndView("order-detail");
        view.addObject("orderId", orderId);
        return view;
    }

    /**
     * Redirect endpoint for response type classification.
     */
    @GetMapping("/redirect")
    public String redirectToList() {
        return "redirect:/orders/list";
    }

    /**
     * Public endpoint with List response and no authentication requirement.
     */
    @GetMapping("/public/recent")
    public Result<List<OrderSummaryVO>> recentPublicOrders(
        @RequestParam(value = "limit", defaultValue = "10") Integer limit,
        HttpServletRequest request) {
        OrderSearchRequest search = new OrderSearchRequest();
        search.setPageSize(limit);
        search.setTenantId(ServletRequestUtils.getStringParameter(request, ApiConstants.HEADER_TENANT_ID, "public"));
        return Result.success(orderService.searchOrders(search, currentUser(request)).getRecords());
    }

    private CurrentUser currentUser(HttpServletRequest request) {
        String tenantId = RequestUtil.requiredTenant(request);
        Long userId = RequestUtil.requiredUserId(request);
        return new CurrentUser(userId, tenantId, "fixture-user", new String[] {"USER", "AUDITOR"});
    }
}
