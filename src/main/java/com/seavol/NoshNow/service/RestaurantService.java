package com.seavol.NoshNow.service;

import com.seavol.NoshNow.dto.requestDTO.ItemRequestDTO;
import com.seavol.NoshNow.dto.requestDTO.RestaurantRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.ItemResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.RestaurantResponseDTO;

import java.util.List;

public interface RestaurantService {
    RestaurantResponseDTO addRestaurant(RestaurantRequestDTO restaurantRequestDTO);

    String changeOpenedStatus(int id);

    RestaurantResponseDTO addMenuItemToRestaurant(ItemRequestDTO itemRequestDTO);

    List<ItemResponseDTO> getMenuOfRestaurant(int id);
}
