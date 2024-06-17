package com.example.webpos.mapper;

import com.example.webpos.model.Product;
import org.mapstruct.Mapper;
import webpos.rest.dto.ProductDto;

import java.util.Collection;

@Mapper(componentModel="spring")
public interface ProductMapper {

    Collection<ProductDto> toProductsDto(Collection<Product> products);

    ProductDto toProductDto(Product product);
}
