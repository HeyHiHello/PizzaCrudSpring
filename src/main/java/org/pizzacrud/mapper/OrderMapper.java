package org.pizzacrud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.dto.OrderDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CustomerMapper.class, PizzaMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
    List<OrderDto> toDtoList(List<Order> orders);
    List<Order> toEntityList(List<OrderDto> orderDtos);
}
