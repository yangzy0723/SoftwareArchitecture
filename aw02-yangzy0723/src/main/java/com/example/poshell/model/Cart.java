package com.example.poshell.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        return items.add(item);
    }

    public boolean emptyItem() {
        items.clear();
        return true;
    }

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
            total += item.getAmount() * item.getProduct().getPrice();
        }

        stringBuilder.append("----------------------\n");

        stringBuilder.append("Total...\t\t\t").append(total);

        return stringBuilder.toString();
    }
}
