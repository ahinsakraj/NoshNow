package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.requestDTO.RestaurantRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.RestaurantResponseDTO;
import com.seavol.NoshNow.model.Restaurant;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RestaurantTransformer {
    public static Restaurant RestaurantRequestDTOToRestaurant(RestaurantRequestDTO restaurantRequestDTO) {
        return Restaurant.builder()
                .name(restaurantRequestDTO.getName())
                .location(restaurantRequestDTO.getLocation())
                .restaurantCategory(restaurantRequestDTO.getRestaurantCategory())
                .contactNumber(restaurantRequestDTO.getContactNumber())
                .opened(false)
                .itemList(new ArrayList<>())
                .orderEntityList(new ArrayList<>())
                .build();
    }

    public static RestaurantResponseDTO RestaurantToRestaurantResponseDTO(Restaurant restaurant) {
        return RestaurantResponseDTO.builder()
                .name(restaurant.getName())
                .contactNumber(restaurant.getContactNumber())
                .location(restaurant.getLocation())
                .opened(restaurant.isOpened())
                .itemResponseDTOList(restaurant.getItemList().stream()
                        .map(ItemTransformer::ItemToItemResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
