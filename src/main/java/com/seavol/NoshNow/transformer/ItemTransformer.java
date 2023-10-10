package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.requestDTO.ItemRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.ItemResponseDTO;
import com.seavol.NoshNow.model.Item;

import java.util.ArrayList;

public class ItemTransformer {

    public static Item ItemRequestToItem(ItemRequestDTO itemRequestDTO) {
        return Item.builder()
                .dishName(itemRequestDTO.getDishName())
                .price(itemRequestDTO.getPrice())
                .category(itemRequestDTO.getFoodCategory())
                .veg(itemRequestDTO.isVeg())
                .available(itemRequestDTO.isAvailable())
                .foodList(new ArrayList<>())
                .build();
    }

    public static ItemResponseDTO ItemToItemResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .dishName(item.getDishName())
                .price(item.getPrice())
                .foodCategory(item.getCategory())
                .veg(item.isVeg())
                .build();
    }
}
