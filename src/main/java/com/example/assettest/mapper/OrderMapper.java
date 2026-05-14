package com.example.assettest.mapper;

import com.example.assettest.dto.OrderSearchRequest;
import com.example.assettest.entity.OrderEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis mapper. Snippet extraction should include the paired XML resultMap
 * when an endpoint depends on these return objects.
 */
@Mapper
public interface OrderMapper {
    OrderEntity selectOrderEntity(@Param("tenantId") String tenantId, @Param("orderId") Long orderId);

    List<OrderEntity> searchOrders(@Param("condition") OrderSearchRequest condition);

    Map<String, Object> selectOrderAttributes(@Param("orderId") Long orderId);
}
