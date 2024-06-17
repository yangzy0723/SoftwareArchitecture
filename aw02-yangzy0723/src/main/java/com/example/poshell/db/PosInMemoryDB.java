package com.example.poshell.db;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PosInMemoryDB implements PosDB {
    private final List<Product> products = new ArrayList<>();

    private Cart cart;

    private PosInMemoryDB() {
        this.products.add(new Product("PD1", "HUAWEI Pocket 2", 7499));
        this.products.add(new Product("PD2", "HUAWEI P60 Art", 8988));
        this.products.add(new Product("PD3", "HUAWEI Mate 60 Pro+", 8999));
        this.products.add(new Product("PD4", "HUAWEI Mate 60 RS 非凡大师", 11999));
        this.products.add(new Product("PD5", "HUAWEI Mate X5", 12999));
        this.products.add(new Product("PD6", "HUAWEI MateBook X Pro 2023 微绒典藏版", 13199));
        this.products.add(new Product("PD7", "MacBook Pro 256GB内存特别版", 99999));
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Cart saveCart(Cart cart) {
        this.cart = cart;
        return this.cart;
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }
}
