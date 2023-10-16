package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.responseDTO.OrderResponseDTO;
import com.seavol.NoshNow.model.OrderEntity;

import java.util.stream.Collectors;

public class OrderTransformer {

    public static OrderResponseDTO OrderToOrderResponseDTO(OrderEntity order) {
        return OrderResponseDTO.builder()
                .customerName(order.getCustomer().getName())
                .restaurantName(order.getRestaurant().getName())
                .foodResponseDTOList(order.getFoodList().stream()
                        .map(FoodTransformer::FoodToFoodResponseDTO)
                        .collect(Collectors.toList()))
                .orderTotal(order.getOrderTotal())
                .coupon("No Coupon Applied")
                .discount(0)
                .grandTotal(order.getOrderTotal())
                .orderId(order.getOrderId())
                .build();
    }


}
