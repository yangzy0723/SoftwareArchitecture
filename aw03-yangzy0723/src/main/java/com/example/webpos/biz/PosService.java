package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.List;

public interface PosService {
    void checkoutCart();

    List<Product> products();

    Cart newCart();

    Cart getCart();

    boolean add(String productId, int amount);

    boolean modifyCart(String productId, int newAmount);

    boolean emptyCart();

    double totalCart();
}
