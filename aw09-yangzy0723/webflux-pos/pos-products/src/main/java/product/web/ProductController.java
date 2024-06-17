package product.web;

import data.mapper.ProductMapper;
import data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.service.ProductService;
import webpos.rest.api.ProductsApi;
import webpos.rest.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api")
public class ProductController implements ProductsApi {

    private final long latency = 1000;

    private final ProductMapper productMapper;
    private final ProductService productService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public ProductController(ProductMapper productMapper, ProductService productService, CircuitBreakerFactory circuitBreakerFactory) {
        this.productMapper = productMapper;
        this.productService = productService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> listProducts(){
        List<ProductDto> products = new ArrayList<>(productMapper.toProductsDto(this.productService.products()));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 使用GET方法根据ID获取特定产品，遵循RESTful原则，使用HTTP 200 OK响应
    @Override
    @GetMapping("/products/{productId}")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<ProductDto> showProductById(@PathVariable String productId) throws InterruptedException {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        TimeUnit.MILLISECONDS.sleep(latency);
        return circuitBreaker.run(
                () -> ResponseEntity.ok(productMapper.toProductDto(productService.getProduct(productId))),
                throwable -> ResponseEntity.ok(productMapper.toProductDto(new Product("null", "未找到任何产品", 0, "null")))
        );
    }
}
