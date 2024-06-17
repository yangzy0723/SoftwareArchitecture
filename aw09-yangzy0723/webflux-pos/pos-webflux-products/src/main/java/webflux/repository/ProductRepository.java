package webflux.repository;


import data.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> allProducts();

    Product findProduct(String productId);
}