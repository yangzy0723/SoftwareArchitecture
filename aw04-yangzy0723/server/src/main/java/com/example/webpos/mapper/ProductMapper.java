package com.example.webpos.mapper;

import com.example.webpos.model.Product;
import com.example.webpos.rest.dto.ProductDto;
import com.example.webpos.rest.dto.ProductQuantityDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel="spring")
public interface ProductMapper {
    Collection<ProductDto> toProductsDto(Collection<Product> products);

    ProductDto toProductDto(Product product);
}
