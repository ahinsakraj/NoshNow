package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.responseDTO.FoodResponseDTO;
import com.seavol.NoshNow.model.Food;
import com.seavol.NoshNow.model.Item;

public class FoodTransformer {

    public static FoodResponseDTO ItemToFoodResponseDTO(Food food) {
        Item item = food.getItem();
        return FoodResponseDTO.builder()
                .dishName(item.getDishName())
                .price(item.getPrice())
                .quantity(food.getQuantity())
                .totalPrice(food.getQuantity() * item.getPrice())
                .foodCategory(item.getCategory())
                .isVeg(item.isVeg())
                .build();
    }

    public static FoodResponseDTO FoodToFoodResponseDTO(Food food) {
        return FoodResponseDTO.builder()
                .dishName(food.getItem().getDishName())
                .price(food.getItem().getPrice())
                .quantity(food.getQuantity())
                .totalPrice(food.getItem().getPrice() * food.getQuantity())
                .foodCategory(food.getItem().getCategory())
                .isVeg(food.getItem().isVeg())
                .build();
    }

    public static Food ItemToFood(Item item) {
        return Food.builder()
                .quantity(1)
                .item(item)
                .build();
    }
}
