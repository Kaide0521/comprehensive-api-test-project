package com.example.assettest.api;

import com.example.assettest.common.Result;
import com.example.assettest.dto.OrderDetailVO;
import com.example.assettest.dto.OrderSummaryVO;
import com.example.assettest.security.CurrentUser;
import com.example.assettest.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * WebFlux-style endpoint fixture for Mono<T> and Flux<T> recursive resolution.
 */
@RestController
@RequestMapping(value = "/api/reactive/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReactiveOrderController {
    private final OrderService orderService;

    public ReactiveOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Reactive order detail")
    public Mono<Result<OrderDetailVO>> monoDetail(
        @PathVariable Long orderId,
        @RequestHeader("X-Tenant-Id") String tenantId) {
        CurrentUser user = new CurrentUser(1001L, tenantId, "reactive-user", new String[] {"USER"});
        return Mono.just(Result.success(orderService.getOrderDetail(tenantId, orderId, user)));
    }

    @GetMapping("/stream")
    @Operation(summary = "Reactive order stream")
    public Flux<OrderSummaryVO> fluxOrders(@RequestHeader("X-Tenant-Id") String tenantId) {
        return Flux.empty();
    }
}
