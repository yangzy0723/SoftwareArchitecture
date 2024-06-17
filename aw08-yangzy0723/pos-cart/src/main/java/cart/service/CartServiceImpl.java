package cart.service;

import cart.model.Cart;
import data.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public boolean deleteProductInCart(Cart cart, String productId) {
        for (int i = 0; i < cart.getItems().size(); ++i) {
            if (cart.getItems().get(i).getProductId().equals(productId)) {
                cart.getItems().remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean emptyCart(Cart cart) {
        cart.getItems().clear();
        return true;
    }

    @Override
    public boolean updateProductInCart(Cart cart, String productId, Integer quantity) {
        cart.getItems().add(new Item(productId, quantity));
        return true;
    }

    @Override
    public List<Item> getCart(Cart cart) {
        return cart.getItems();
    }
}
