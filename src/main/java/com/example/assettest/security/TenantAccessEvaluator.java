package com.example.assettest.security;

import com.example.assettest.dto.OrderDetailVO;
import org.springframework.stereotype.Component;

/**
 * Authorization helper for tenant and owner isolation.
 */
@Component
public class TenantAccessEvaluator {
    public boolean canReadOrder(CurrentUser user, String tenantId, Long orderId, OrderDetailVO detail) {
        if (user == null || tenantId == null || detail == null) {
            return false;
        }
        boolean tenantMatches = tenantId.equals(user.getTenantId()) && tenantId.equals(detail.getTenantId());
        boolean ownerMatches = detail.getOwnerUserId() != null && detail.getOwnerUserId().equals(user.getId());
        return tenantMatches && ownerMatches;
    }

    public void assertTenant(String requestTenantId, CurrentUser user) {
        if (user == null || !requestTenantId.equals(user.getTenantId())) {
            throw new SecurityException("tenant mismatch");
        }
    }
}
