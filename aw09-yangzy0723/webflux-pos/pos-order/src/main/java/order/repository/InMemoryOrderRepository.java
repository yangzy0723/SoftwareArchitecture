package order.repository;

import data.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 暂时是使用的in-memory repository
 * 不足: 1. 只能启动一个实例, 不然会导致订单重复;  2. 不能持久化
 */
@Component
public class InMemoryOrderRepository implements OrderRepository {
    static private final HashMap<Integer, Order> orders = new HashMap<>();
    static private int counts = 0;

    public synchronized List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public synchronized Order findById(Integer id) {
        return orders.getOrDefault(id, null);
    }

    public synchronized Order addOrder(Order order) {
        counts += 1;
        order.setId(counts);
        orders.put(order.getId(), order);
        return order;
    }

    public synchronized boolean deleteOrder(Integer id) {
        return orders.remove(id) != null;
    }
}
