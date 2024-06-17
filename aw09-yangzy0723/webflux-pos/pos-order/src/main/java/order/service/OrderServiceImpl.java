package order.service;

import data.model.Item;
import data.model.Order;
import order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    StreamBridge streamBridge;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, StreamBridge streamBridge) {
        this.orderRepository = orderRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order addOrder(List<Item> items) {
        Order order = new Order();
        order.setItems(items);
        orderRepository.addOrder(order);
        return order;
    }

    @Override
    public Order getOrder(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order deliverOrder(Integer id) {
        Order order = getOrder(id);
        if (order == null) {
            return null;
        }
        orderRepository.deleteOrder(id);
        streamBridge.send("delivery", order);
        System.out.println("delivery order " + id);
        return order;
    }
}
