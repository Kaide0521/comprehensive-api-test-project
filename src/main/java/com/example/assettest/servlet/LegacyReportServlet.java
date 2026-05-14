package com.example.assettest.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.assettest.common.ApiResponse;
import com.example.assettest.dto.OrderSearchRequest;
import com.example.assettest.util.JsonUtil;
import com.example.assettest.util.ParamUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Legacy servlet endpoint for testing @WebServlet and doGet/doPost extraction.
 */
@WebServlet(urlPatterns = {"/legacy/report", "/legacy/report/*"})
public class LegacyReportServlet extends HttpServlet {
    /**
     * Report download query endpoint.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenantId = request.getHeader("X-Tenant-Id");
        String reportType = request.getParameter("reportType");
        String format = request.getParameter("format");
        Object userId = request.getAttribute("userId");
        Map<String, Object> payload = new HashMap<>();
        payload.put("tenantId", tenantId);
        payload.put("reportType", reportType);
        payload.put("format", format);
        payload.put("userId", userId);
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(ApiResponse.ok(payload)));
    }

    /**
     * Report creation endpoint with JSON request body.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rawText = request.getReader().readLine();
        JSONObject parsed = JSONObject.parseObject(rawText);
        Integer priority = ParamUtil.parseInteger(request.getParameter("priority"), 1);
        OrderSearchRequest body = JsonUtil.readBody(request, OrderSearchRequest.class);
        body.setPageSize(priority);
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(ApiResponse.ok(parsed != null ? parsed : body)));
    }
}
