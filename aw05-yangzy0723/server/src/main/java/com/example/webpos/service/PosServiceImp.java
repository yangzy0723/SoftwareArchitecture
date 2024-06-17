package com.example.webpos.service;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosServiceImp implements ProductService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    @Cacheable(value = "products")
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    @Cacheable(value = "product", key = "#productId")
    public Product getProduct(String productId) {
        return posDB.getProduct(productId);
    }

    @Override
    public Product setProductQuantity(String productId, int quantity) {
        return posDB.setProductQuantity(productId, quantity);
    }
}
