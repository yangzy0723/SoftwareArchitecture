package order.service;

import data.model.Item;
import data.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();

    Order addOrder(List<Item> items);

    Order getOrder(Integer id);

    Order deliverOrder(Integer id);
}
