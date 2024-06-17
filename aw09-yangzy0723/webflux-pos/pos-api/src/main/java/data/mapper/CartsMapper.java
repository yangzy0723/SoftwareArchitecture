package data.mapper;

import data.model.Item;
import org.mapstruct.Mapper;
import webpos.rest.dto.ItemDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CartsMapper {
    Collection<ItemDto> toCartDto(Collection<Item> items);

    Collection<Item> toCart(Collection<ItemDto> cartItems);

    ItemDto toItemDto(Item cartItem);

    Item toItem(ItemDto cartItem);
}
