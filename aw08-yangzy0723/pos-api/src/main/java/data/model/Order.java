package data.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Order implements Serializable {
    private Integer id;
    private List<Item> items;

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", items=" + items + '}';
    }
}
