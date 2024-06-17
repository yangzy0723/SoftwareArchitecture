package webflux.web;

import data.mapper.ProductMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.service.ProductService;
import webpos.rest.dto.ProductDto;

import java.time.Duration;

@RestController
@RequestMapping("webflux")
public class ProductWebFluxController {

    private final long latency = 1000;

    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductWebFluxController(ProductMapper productMapper, ProductService productService) {
        this.productMapper = productMapper;
        this.productService = productService;
    }

    @GetMapping("/products")
    public Flux<ProductDto> listProducts() {
        return productService.products().map(productMapper::toProductDto);
    }

    @GetMapping("/products/{productId}")
    public Mono<ProductDto> showProductById(@PathVariable String productId) {
        return productService.getProduct(productId).map(productMapper::toProductDto).delayElement(Duration.ofMillis(latency));
    }
}
