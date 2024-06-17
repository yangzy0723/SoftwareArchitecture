package com.example.webpos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "items")
@AllArgsConstructor
public class Item extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "quantity")
    private int quantity;

    public Item() {

    }

    @Override
    public String toString() {
        return product.toString() + "\t" + quantity;
    }
}
