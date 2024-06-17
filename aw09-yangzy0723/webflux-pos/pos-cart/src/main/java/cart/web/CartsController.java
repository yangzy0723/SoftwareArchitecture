package cart.web;

import cart.model.Cart;
import cart.service.CartService;
import data.mapper.CartsMapper;
import data.model.Item;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import webpos.rest.api.CartApi;
import webpos.rest.dto.ItemDto;
import webpos.rest.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartsController implements CartApi {
    Cart cart;
    CartService cartService;
    CartsMapper cartsMapper;

    @LoadBalanced
    RestTemplate restTemplate;

    @Autowired
    public CartsController(Cart cart, CartService cartService, CartsMapper cartsMapper, RestTemplate restTemplate) {
        this.cart = cart;
        this.cartService = cartService;
        this.cartsMapper = cartsMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<OrderDto> checkOutCart() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Item>> entity = new HttpEntity<>(cart.getItems(), headers);
        return restTemplate.postForEntity("http://order-service/api/order/new", entity, OrderDto.class);
    }

    @Override
    public ResponseEntity<Boolean> deleteProductInCart(String productId) {
        if (cartService.deleteProductInCart(cart, productId))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @Override
    public ResponseEntity<Boolean> emptyCart() {
        if (cartService.emptyCart(cart))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @Override
    public ResponseEntity<List<ItemDto>> getCart() {
        return new ResponseEntity<>(new ArrayList<>(cartsMapper.toCartDto(cartService.getCart(cart))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> updateProductInCart(@PathVariable String productId, @PathVariable Integer quantity) {
        if (cartService.updateProductInCart(cart, productId, quantity))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
}
