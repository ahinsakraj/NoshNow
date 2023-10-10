package com.seavol.NoshNow.utils;

import com.seavol.NoshNow.exception.CustomerNotFoundException;
import com.seavol.NoshNow.exception.ItemNotFoundException;
import com.seavol.NoshNow.exception.RestaurantNotFoundException;
import com.seavol.NoshNow.model.Customer;
import com.seavol.NoshNow.model.Item;
import com.seavol.NoshNow.model.Restaurant;
import com.seavol.NoshNow.repository.CustomerRepository;
import com.seavol.NoshNow.repository.ItemRepository;
import com.seavol.NoshNow.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationUtils {

    final RestaurantRepository restaurantRepository;
    final CustomerRepository customerRepository;
    final ItemRepository itemRepository;

    @Autowired
    public ValidationUtils(RestaurantRepository restaurantRepository,
                           CustomerRepository customerRepository,
                           ItemRepository itemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    public Restaurant validateRestaurant(int id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if(optionalRestaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Invalid Restaurant Id");
        } else {
            return optionalRestaurant.get();
        }
    }

    public Customer validateCustomer(String mobileNo) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByMobileNo(mobileNo));
        if(optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Invalid Customer Id");
        } else {
            return optionalCustomer.get();
        }
    }

    public Item validateItem(int id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty()) {
            throw new ItemNotFoundException("Invalid Food Id");
        } else {
            return optionalItem.get();
        }
    }
}
