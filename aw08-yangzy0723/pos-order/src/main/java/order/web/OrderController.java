package order.web;

import data.mapper.ItemMapper;
import data.mapper.OrderMapper;
import data.model.Item;
import data.model.Order;
import order.service.OrderService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webpos.rest.api.OrderApi;
import webpos.rest.dto.ItemDto;
import webpos.rest.dto.OrderDto;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController implements OrderApi {

    OrderService orderService;
    OrderMapper orderMapper;
    ItemMapper itemMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper, ItemMapper itemMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public ResponseEntity<OrderDto> addOrder(List<ItemDto> items) {
        Order order = orderService.addOrder((List<Item>) itemMapper.toCart(items));
        if (order == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> deliverOrderById(@PathVariable Integer orderId) {
        Order order = orderService.deliverOrder(orderId);
        if (order == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> allOrders() {
        Collection<Order> orders = orderService.getAllOrder();
        if (orders == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>((List<OrderDto>) orderMapper.toOrdersDto(orders), HttpStatus.OK);
    }
}
