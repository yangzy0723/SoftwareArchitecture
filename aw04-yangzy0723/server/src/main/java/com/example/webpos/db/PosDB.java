package com.example.webpos.db;

import com.example.webpos.model.Product;

import java.util.List;

public interface PosDB {
    List<Product> getProducts();
    Product getProduct(String productId);
    Product setProductQuantity(String productId, int quantity);
}
