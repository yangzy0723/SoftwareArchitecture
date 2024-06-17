package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public void checkoutCart() {
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public Cart getCart() {
        return posDB.getCart();
    }


    @Override
    public boolean add(String productId, int amount) {
        return posDB.addProduct(productId, amount);
    }

    @Override
    public boolean modifyCart(String productId, int newAmount) {
        return posDB.modifyCart(productId, newAmount);
    }

    @Override
    public boolean emptyCart() {
        return posDB.emptyCart();
    }

    @Override
    public double totalCart() {
        List<Item> items = this.getCart().getItems();
        double sum = 0;
        for (Item item : items)
            sum += item.getProduct().getPrice() * item.getQuantity();
        return sum;
    }
}
