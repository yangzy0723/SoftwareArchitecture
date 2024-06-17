package com.example.webpos.service;

import com.example.webpos.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> products();
    Product getProduct(String productId);
    Product setProductQuantity(String productId, int quantity);
}
