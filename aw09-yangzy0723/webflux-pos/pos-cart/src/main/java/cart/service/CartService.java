package cart.service;

import cart.model.Cart;
import data.model.Item;

import java.util.List;

public interface CartService {
    boolean deleteProductInCart(Cart cart, String productId);

    boolean emptyCart(Cart cart);

    boolean updateProductInCart(Cart cart, String productId, Integer quantity);

    List<Item> getCart(Cart cart);
}
