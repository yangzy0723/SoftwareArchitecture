package com.example.webpos.rest;

import com.example.webpos.model.Product;
import com.example.webpos.service.ProductService;
import com.example.webpos.mapper.ProductMapper;
import com.example.webpos.rest.api.ProductApi;
import com.example.webpos.rest.dto.ProductDto;
import com.example.webpos.rest.dto.ProductQuantityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product") // 定义API的基础路径
public class ProductController implements ProductApi {

    private final ProductService posService;
    private final ProductMapper productMapper;

    // Constructor injection for service and mapper
    public ProductController(ProductMapper productMapper, ProductService posService) {
        this.posService = posService;
        this.productMapper = productMapper;
    }

    @Override
    // 使用GET方法列出所有产品，遵循RESTful原则，使用HTTP 200 OK响应
    @GetMapping
    /*
      This annotation enables Cross-Origin Resource Sharing (CORS) for the annotated class or method.
      CORS is a mechanism that allows many resources (e.g., fonts, JavaScript, etc.) on a web page
      to be requested from another domain outside the domain from which the resource originated.

      The parameters for the annotation are as follows:

      - value = "*": This specifies that any domain is allowed to make cross-origin requests.
        For security reasons, it's generally advised to specify explicit domains rather than using a wildcard.

      - maxAge = 1800: This is the maximum age (in seconds) of the preflight request. A preflight request is
        the HTTP OPTIONS request that checks server support for the actual request before the actual
        request is sent. This value indicates how long the result of the preflight request can be cached.

      - allowedHeaders = "*": This indicates that any HTTP header is allowed to be used in the actual request.
        For security, it's recommended to specify the exact headers that are needed.

      It's important to note that while using "*" for both the origin and headers provides flexibility,
      it can also introduce security risks, and should be used with caution, especially in a production environment.
     */
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<List<ProductDto>> listProducts() {
        List<ProductDto> products = new ArrayList<>(productMapper.toProductsDto(posService.products()));
        if (products.isEmpty()) {
            // 使用HTTP 404 Not Found，如果没有任何产品可列出
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products); // 使用HTTP 200 OK
    }

    // 使用GET方法根据ID获取特定产品，遵循RESTful原则，使用HTTP 200 OK响应
    @Override
    @GetMapping("/{productId}")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<ProductDto> showProductById(@PathVariable String productId) {
        return ResponseEntity.ok(productMapper.toProductDto(posService.getProduct(productId)));
    }


    @Override
    @PatchMapping("/{productId}")
    @CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
    public ResponseEntity<ProductDto> productProductIdPatch(@PathVariable String productId, @RequestBody ProductQuantityDto productQuantityDto) {
        int newQuantity = productQuantityDto.getQuantity();
        Product ret = posService.setProductQuantity(productId, newQuantity);
        if(ret == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(productMapper.toProductDto(ret));
    }
}
