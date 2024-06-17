package product.service;

import data.model.Product;

import java.util.List;


public interface ProductService {

    List<Product> products();

    Product getProduct(String id);
}
