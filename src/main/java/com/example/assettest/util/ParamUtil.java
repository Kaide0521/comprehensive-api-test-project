package com.example.assettest.util;

/**
 * Custom parameter utility used to test project-specific parser inference.
 */
public final class ParamUtil {
    private ParamUtil() {
    }

    public static String firstNonBlank(String first, String second) {
        if (first != null && !first.trim().isEmpty()) {
            return first;
        }
        return second;
    }

    public static Integer parseInteger(String value, Integer defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }
}
