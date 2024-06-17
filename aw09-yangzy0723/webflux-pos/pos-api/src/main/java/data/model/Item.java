package data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private String productId;
    private int quantity;

    @Override
    public String toString() {
        return "Item{" + "productId='" + productId + '\'' + ", quantity=" + quantity + '}';
    }
}
