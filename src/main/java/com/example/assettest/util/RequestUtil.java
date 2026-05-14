package com.example.assettest.util;

import com.example.assettest.common.ApiConstants;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * Custom request parser. Endpoint snippets should include methods referenced by
 * controllers because they reveal dynamic query, header, cookie, and form
 * parameters not visible in method signatures.
 */
public final class RequestUtil {
    private RequestUtil() {
    }

    public static String requiredTenant(HttpServletRequest request) {
        return request.getHeader(ApiConstants.HEADER_TENANT_ID);
    }

    public static Long requiredUserId(HttpServletRequest request) {
        return ServletRequestUtils.getLongParameter(request, "userId", 0L);
    }

    public static String optionalChannel(HttpServletRequest request) {
        return request.getParameter("channel");
    }

    public static String sessionToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (ApiConstants.COOKIE_SESSION.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
