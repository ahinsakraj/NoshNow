package com.seavol.NoshNow.service.impl;

import com.seavol.NoshNow.dto.requestDTO.ItemRequestDTO;
import com.seavol.NoshNow.dto.requestDTO.RestaurantRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.ItemResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.RestaurantResponseDTO;
import com.seavol.NoshNow.model.Item;
import com.seavol.NoshNow.model.Restaurant;
import com.seavol.NoshNow.repository.ItemRepository;
import com.seavol.NoshNow.repository.RestaurantRepository;
import com.seavol.NoshNow.service.RestaurantService;
import com.seavol.NoshNow.transformer.ItemTransformer;
import com.seavol.NoshNow.transformer.RestaurantTransformer;
import com.seavol.NoshNow.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    final RestaurantRepository restaurantRepository;
    final ItemRepository itemRepository;
    final ValidationUtils validationUtils;
    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 ItemRepository itemRepository,
                                 ValidationUtils validationUtils) {
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public RestaurantResponseDTO addRestaurant(RestaurantRequestDTO restaurantRequestDTO) {

        Restaurant restaurant = RestaurantTransformer.RestaurantRequestDTOToRestaurant(restaurantRequestDTO);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantTransformer.RestaurantToRestaurantResponseDTO(savedRestaurant);
    }

    @Override
    public String changeOpenedStatus(int id) {

        Restaurant restaurant = validationUtils.validateRestaurant(id);
        restaurant.setOpened(!restaurant.isOpened());
        restaurantRepository.save(restaurant);
        if(restaurant.isOpened()) {
            return "Restaurant is OPEN";
        }  else {
            return "Restaurant is CLOSE";
        }
    }

    @Override
    public RestaurantResponseDTO addMenuItemToRestaurant(ItemRequestDTO itemRequestDTO) {
        // get the restaurant
        Restaurant restaurant = validationUtils.validateRestaurant(itemRequestDTO.getRestaurantId());
        // prepare the item by request
        Item item = ItemTransformer.ItemRequestToItem(itemRequestDTO);

        // map the relation
        item.setRestaurant(restaurant);
        restaurant.getItemList().add(item);

        // Save restaurant and food then return the response
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantTransformer.RestaurantToRestaurantResponseDTO(savedRestaurant);
    }

    @Override
    public List<ItemResponseDTO> getMenuOfRestaurant(int id) {
        Restaurant restaurant = validationUtils.validateRestaurant(id);
        List<Item> itemList = restaurant.getItemList();
        return itemList.stream()
                .map(ItemTransformer::ItemToItemResponseDTO)
                .collect(Collectors.toList());
    }


}
