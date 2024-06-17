package com.example.webpos.service;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PosServiceImp implements ProductService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public Product getProduct(String productId) {
        return posDB.getProduct(productId);
    }

    @Override
    public Product setProductQuantity(String productId, int quantity) {
        return posDB.setProductQuantity(productId, quantity);
    }
}
