package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PosInMemoryDB implements PosDB {
    private final List<Product> products = new ArrayList<>();

    private Cart cart;

    private PosInMemoryDB() {
        this.products.add(new Product("PD1", "HUAWEI Pocket 2", 7499, "1.png"));
        this.products.add(new Product("PD2", "HUAWEI P60 Art", 8988, "2.png"));
        this.products.add(new Product("PD3", "HUAWEI Mate 60 Pro+", 8999, "3.png"));
        this.products.add(new Product("PD4", "HUAWEI Mate 60 RS 非凡大师", 11999, "4.png"));
        this.products.add(new Product("PD5", "HUAWEI Mate X5", 12999, "5.png"));
        this.products.add(new Product("PD6", "HUAWEI MateBook X Pro 2023 微绒典藏版", 13199, "6.png"));
        this.products.add(new Product("PD7", "HUAWEI MatePad Pro 13.2英寸", 13199, "7.png"));
        this.products.add(new Product("PD8", "MacBook Pro 256GB内存特别版", 99999, "8.png"));
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
        for (Product p : getProducts())
            if (p.getPid().equals(productId))
                return p;
        return null;
    }

    @Override
    public boolean addProduct(String productId, int amount) {
        List<Item> items = cart.getItems();
        for (Item item : items)
            if (Objects.equals(item.getProduct().getPid(), productId)) {
                item.setQuantity(item.getQuantity() + amount);
                return true;
            }
        items.add(new Item(getProduct(productId), cart, amount));
        return true;
    }

    @Override
    public boolean modifyCart(String productId, int newAmount) {
        List<Item> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (Objects.equals(items.get(i).getProduct().getPid(), productId)) {
                if (newAmount == 0)
                    items.remove(i);
                else
                    items.get(i).setQuantity(newAmount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean emptyCart() {
        List<Item> items = cart.getItems();
        items.clear();
        return true;
    }
}
