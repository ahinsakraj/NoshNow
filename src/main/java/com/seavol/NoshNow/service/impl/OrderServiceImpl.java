package com.seavol.NoshNow.service.impl;

import com.seavol.NoshNow.Enum.CouponType;
import com.seavol.NoshNow.dto.responseDTO.AppliedCouponResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.OrderResponseDTO;
import com.seavol.NoshNow.exception.DeliveryPartnerException;
import com.seavol.NoshNow.exception.InvalidCouponException;
import com.seavol.NoshNow.model.*;
import com.seavol.NoshNow.repository.*;
import com.seavol.NoshNow.service.OrderService;
import com.seavol.NoshNow.transformer.OrderTransformer;
import com.seavol.NoshNow.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    final ValidationUtils validationUtils;
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final CouponRepository couponRepository;
    final FoodRepository foodRepository;
    final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ValidationUtils validationUtils,
                            DeliveryPartnerRepository deliveryPartnerRepository,
                            CouponRepository couponRepository, FoodRepository foodRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.validationUtils = validationUtils;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.couponRepository = couponRepository;
        this.foodRepository = foodRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public AppliedCouponResponseDTO applyCoupon(String mobile, String couponName) {
        // Validate cart and coupon
        Cart cart = validationUtils.validateCart(mobile);
        Coupon coupon = validationUtils.validateCoupon(couponName);

        // Check if coupon is applicable at this price
        if(cart.getCartTotal() < coupon.getApplicableValue()) {
            throw new InvalidCouponException("Add some more item to apply the coupon");
        }
        int discount = calculateDiscount(cart.getCartTotal(), coupon);

        return AppliedCouponResponseDTO.builder()
                .message("Coupon added successfully")
                .orderTotal(cart.getCartTotal())
                .coupon(coupon.getName())
                .discount(discount)
                .grandTotal(cart.getCartTotal() - discount)
                .build();
    }

    @Override
    public AppliedCouponResponseDTO removeCoupon(String mobile) {
        Cart cart = validationUtils.validateCart(mobile);
        return AppliedCouponResponseDTO.builder()
                .message("Coupon removed successfully")
                .orderTotal(cart.getCartTotal())
                .coupon("No Coupon Applied")
                .discount(0)
                .grandTotal(cart.getCartTotal())
                .build();
    }

    @Override
    public OrderResponseDTO placeOrder(String mobile) {
        // Prepare Order and save it
        OrderEntity order = prepareOrder(mobile);
        orderRepository.save(order);

        // Validate cart to make it empty
        Cart cart = validationUtils.validateCart(mobile);

        // Extract food list
        List<Food> foodList = new ArrayList<>(cart.getFoodList());

        // Empty the cart
        cart.getFoodList().clear();
        cartRepository.save(cart);

        // Remove food from cart and add it to order
        for(Food food : foodList) {
            food.setCart(null);
            food.setOrderEntity(order);
            foodRepository.save(food);
        }


        // Set foodList and Save the order
        order.setFoodList(foodList);
        orderRepository.save(order);

        return OrderTransformer.OrderToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO placeOrderWithCoupon(String mobile, String couponName) {
        OrderEntity order = prepareOrder(mobile);
        addCoupon(order, couponName);
        orderRepository.save(order);



        // Validate cart to make it empty
        Cart cart = validationUtils.validateCart(mobile);

        // Extract food list
        List<Food> foodList = new ArrayList<>(cart.getFoodList());

        // Empty the cart
        cart.getFoodList().clear();
        cartRepository.save(cart);

        // Remove food from cart and add it to order
        for(Food food : foodList) {
            food.setCart(null);
            food.setOrderEntity(order);
            foodRepository.save(food);
        }


        // Set foodList and Save the order
        order.setFoodList(foodList);
        orderRepository.save(order);

        // Prepare the OrderResponseDTO and set the proper values
        OrderResponseDTO orderResponseDTO = OrderTransformer.OrderToOrderResponseDTO(order);
        orderResponseDTO.setCoupon(couponName);
        int discount = calculateDiscount(order.getOrderTotal(), order.getCoupon());
        orderResponseDTO.setDiscount(discount);
        orderResponseDTO.setGrandTotal(order.getOrderTotal() - discount);
        return orderResponseDTO;
    }

    public void addCoupon(OrderEntity order, String couponName) {
        // Validate Coupon
        Coupon coupon = validationUtils.validateCoupon(couponName);
        double applicableValue = coupon.getApplicableValue();
        double orderTotal = order.getOrderTotal();

        // Check if coupon is applicable at this price
        if(orderTotal < applicableValue) {
            throw new InvalidCouponException("Add some more item to apply the coupon");
        }
        order.setCoupon(coupon);
    }

    public int calculateDiscount(double cartTotal, Coupon coupon) {
        double discount;
        if(coupon.getCouponType() == CouponType.CASHBACK) {
            discount = coupon.getCashback();
        } else if(coupon.getCouponType() == CouponType.FLAT_OFF) {
            discount = coupon.getFlatOff();
        } else {
            discount = cartTotal * coupon.getPercentOff() / 100;
        }
        return (int)(discount);
    }

    public OrderEntity prepareOrder(String mobile) {
        // Validate Customer and Cart
        Customer customer = validationUtils.validateCustomer(mobile);
        Cart cart = validationUtils.validateCart(mobile);

        // Check if any delivery partner is available and pick one
        if(deliveryPartnerRepository.findAll().isEmpty()) {
            throw new DeliveryPartnerException("Sorry our delivery partner is not available");
        }
        DeliveryPartner deliveryPartner = deliveryPartnerRepository.findRandomDeliveryPartner();

        // Initialize order and set values
        OrderEntity order = new OrderEntity();
        order.setOrderId(String.valueOf(UUID.randomUUID()));
        order.setOrderTotal(cart.getCartTotal());
        order.setCustomer(customer);
        order.setDeliveryPartner(deliveryPartner);
        order.setRestaurant(cart.getFoodList().get(0).getItem().getRestaurant());
        return order;
    }
}
