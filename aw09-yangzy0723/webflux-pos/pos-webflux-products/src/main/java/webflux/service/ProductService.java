package webflux.service;

import data.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> products();

    Mono<Product> getProduct(String id);
}