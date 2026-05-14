package com.example.assettest.common;

/**
 * API constants that should be retained only when referenced by endpoint code.
 */
public final class ApiConstants {
    public static final String HEADER_TENANT_ID = "X-Tenant-Id";
    public static final String HEADER_TRACE_ID = "X-Trace-Id";
    public static final String COOKIE_SESSION = "SESSION";
    public static final String MEDIA_JSON = "application/json";
    public static final int DEFAULT_PAGE_SIZE = 20;

    private ApiConstants() {
    }
}
