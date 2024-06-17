package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import com.example.webpos.repository.CartRepository;
import com.example.webpos.repository.ItemRepository;
import com.example.webpos.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Data
@Service
public class PosInH2DB implements PosDB {
    private ProductRepository productRepository;
    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    @Autowired
    public PosInH2DB(ProductRepository productRepository, ItemRepository itemRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Cart saveCart(Cart cart) {
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart getCart() {
        // TODO: 目前只实现一辆购物车的情况，应当有改进余地
        return cartRepository.getReferenceById(1L);
    }

    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public boolean addProduct(String productId, int amount) {
        Cart cart = getCart();
        List<Item> items = cart.getItems();
        for (Item item : items)
            if (Objects.equals(item.getProduct().getPid(), productId)) {
                item.setQuantity(item.getQuantity() + amount);
                cartRepository.save(cart);
                return true;
            }
        items.add(new Item(getProduct(productId), cart, amount));
        cartRepository.save(cart);
        return true;
    }

    @Override
    public boolean modifyCart(String productId, int newAmount) {
        Item item = itemRepository.findByProductId(productId);
        if(newAmount == 0)
            itemRepository.delete(item);
        else {
            item.setQuantity(newAmount);
            itemRepository.save(item);
        }
        return true;

        //TODO: 下面方法无法进行正常删除操作，原因有待探究...
//        Cart cart = getCart();
//        List<Item> items = cart.getItems();
//        for (Item item : items) {
//            if (Objects.equals(item.getProduct().getPid(), productId)) {
//                if (newAmount == 0) {
//                    itemRepository.delete(item);
//                    return true;
//                } else {
//                    item.setQuantity(newAmount);
//                    cartRepository.save(cart);
//                }
//                return true;
//            }
//        }
//        return false;
    }

    @Override
    public boolean emptyCart() {
        itemRepository.deleteAll();
        return true;
    }
}