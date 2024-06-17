package data.mapper;

import data.model.Order;
import org.mapstruct.Mapper;
import webpos.rest.dto.OrderDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Collection<OrderDto> toOrdersDto(Collection<Order> orders);

    Collection<Order> toOrders(Collection<OrderDto> orderDtos);

    OrderDto toOrderDto(Order order);

    Order toOrder(OrderDto orderDto);
}
