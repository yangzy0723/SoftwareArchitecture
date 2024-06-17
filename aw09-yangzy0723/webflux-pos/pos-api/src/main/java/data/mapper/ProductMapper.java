package data.mapper;

import data.model.Product;
import org.mapstruct.Mapper;
import webpos.rest.dto.ProductDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Collection<ProductDto> toProductsDto(Collection<Product> products);

    Collection<Product> toProducts(Collection<ProductDto> products);

    Product toProduct(ProductDto productDto);

    ProductDto toProductDto(Product product);
}
