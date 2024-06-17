package data.mapper;

import data.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import webpos.rest.dto.ItemDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Collection<ItemDto> toCartDto(Collection<Item> items);

    Collection<Item> toCart(Collection<ItemDto> cartItems);

    ItemDto toItemDto(Item cartItem);

    Item toItem(ItemDto cartItem);
}
