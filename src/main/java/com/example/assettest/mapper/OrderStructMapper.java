package com.example.assettest.mapper;

import com.example.assettest.dto.OrderDetailVO;
import com.example.assettest.dto.OrderSummaryVO;
import com.example.assettest.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct-style converter used to infer response DTOs from persistence
 * objects returned by service and mapper methods.
 */
@Mapper
public interface OrderStructMapper {
    OrderStructMapper INSTANCE = Mappers.getMapper(OrderStructMapper.class);

    @Mapping(source = "id", target = "orderId")
    OrderDetailVO toDetail(OrderEntity entity);

    @Mapping(source = "customerName", target = "customerName")
    OrderSummaryVO toSummary(OrderEntity entity);
}
