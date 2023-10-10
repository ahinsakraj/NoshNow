package com.seavol.NoshNow.controller;

import com.seavol.NoshNow.dto.requestDTO.ItemRequestDTO;
import com.seavol.NoshNow.dto.requestDTO.RestaurantRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.ItemResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.RestaurantResponseDTO;
import com.seavol.NoshNow.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantResponseDTO> addRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantResponseDTO restaurantResponseDTO = restaurantService.addRestaurant(restaurantRequestDTO);
        return new ResponseEntity<>(restaurantResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/status")
    public ResponseEntity<String> changeOpenedStatus(@RequestParam int id){
        try {
            String message = restaurantService.changeOpenedStatus(id);
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/menu-item")
    public ResponseEntity<?> addMenuItemToRestaurant(@RequestBody ItemRequestDTO itemRequestDTO) {
        try {
            RestaurantResponseDTO restaurantResponseDTO = restaurantService.addMenuItemToRestaurant(itemRequestDTO);
            return new ResponseEntity<>(restaurantResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-menu/{id}")
    public ResponseEntity<?> getMenuOfRestaurant(@PathVariable int id) {
        try {
            List<ItemResponseDTO> itemResponseDTOList = restaurantService.getMenuOfRestaurant(id);
            return new ResponseEntity<>(itemResponseDTOList, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
