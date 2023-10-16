package com.seavol.NoshNow.utils;

import com.seavol.NoshNow.exception.*;
import com.seavol.NoshNow.model.*;
import com.seavol.NoshNow.repository.CouponRepository;
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
    final CouponRepository couponRepository;

    @Autowired
    public ValidationUtils(RestaurantRepository restaurantRepository,
                           CustomerRepository customerRepository,
                           ItemRepository itemRepository, CouponRepository couponRepository) {
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.couponRepository = couponRepository;
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

    public Cart validateCart(String mobile) {
        Customer customer = validateCustomer(mobile);
        Cart cart = customer.getCart();
        if(cart.getFoodList().isEmpty()) {
            throw new InvalidQuantityException("cart is empty");
        }
        return cart;
    }

    public Coupon validateCoupon(String couponName) {
        // Validate Coupon
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByName(couponName));
        if(optionalCoupon.isEmpty()) {
            throw new InvalidCouponException("Enter a valid Coupon");
        }
        return optionalCoupon.get();
    }
}
