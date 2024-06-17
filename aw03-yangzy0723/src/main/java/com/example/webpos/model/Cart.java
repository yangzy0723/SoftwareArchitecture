package com.example.webpos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<Item> items = new ArrayList<>();

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n");

        for (Item item : items) {
            stringBuilder.append(item.toString()).append("\n");
            total += item.getQuantity() * item.getProduct().getPrice();
        }

        stringBuilder.append("----------------------\n");

        stringBuilder.append("Total...\t\t\t").append(total);

        return stringBuilder.toString();
    }
}
