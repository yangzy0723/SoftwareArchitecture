package com.example.webpos.db;

import com.example.webpos.model.Product;
import com.example.webpos.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PosInH2DB implements PosDB {
    private ProductRepository productRepository;

    @Autowired
    public PosInH2DB(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product setProductQuantity(String productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null)
            return null;
        product.setQuantity(quantity);
        productRepository.save(product);
        return product;
    }

}