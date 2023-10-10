package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.responseDTO.CartResponseDTO;
import com.seavol.NoshNow.model.Cart;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CartTransformer {

    public static Cart prepareCart() {
        return Cart.builder()
                .cartTotal(0)
                .foodList(new ArrayList<>())
                .build();
    }

    public static CartResponseDTO cartTocartResponseDTO(Cart cart) {
        return CartResponseDTO.builder()
                .customerName(cart.getCustomer().getName())
                .customerMobile(cart.getCustomer().getMobileNo())
                .cartTotal(cart.getCartTotal())
                .foodBookingList(cart.getFoodList().stream()
                        .map(FoodTransformer::ItemToFoodResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
