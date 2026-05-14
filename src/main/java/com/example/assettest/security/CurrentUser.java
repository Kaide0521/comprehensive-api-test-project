package com.example.assettest.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Authenticated user principal used by owner and tenant checks.
 */
@Data
@AllArgsConstructor
public class CurrentUser {
    private Long id;
    private String tenantId;
    private String username;
    private String[] roles;
}
