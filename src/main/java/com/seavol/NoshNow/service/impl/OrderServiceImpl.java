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
import java.util.Optional;
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
    public OrderResponseDTO placeOrder(String mobile) {
        OrderEntity order = prepareOrder(mobile);
        return OrderTransformer.OrderToOrderResponseDTO(order);
    }

    @Override
    public AppliedCouponResponseDTO applyCoupon(String mobile, String couponName) {
        OrderEntity order = addCoupon(mobile, couponName);

        // Validate cart to pass as argument and return response
        Cart cart = validationUtils.validateCart(mobile);
        OrderResponseDTO orderResponseDTO = prepareOrderResponseDTO(order, cart);

        // This is a post made change and code is needed to rebuild and reformat and cleaned
        return AppliedCouponResponseDTO.builder()
                .message("Coupon added successfully")
                .orderTotal(orderResponseDTO.getOrderTotal())
                .coupon(orderResponseDTO.getCoupon())
                .discount(orderResponseDTO.getDiscount())
                .grandTotal(orderResponseDTO.getGrandTotal())
                .build();
    }

    @Override
    public AppliedCouponResponseDTO removeCoupon(String mobile) {
        OrderResponseDTO orderResponseDTO = placeOrder(mobile);

        // This is a post made change and code is needed to rebuild and reformat and cleaned
        return AppliedCouponResponseDTO.builder()
                .message("Coupon removed successfully")
                .orderTotal(orderResponseDTO.getOrderTotal())
                .coupon(orderResponseDTO.getCoupon())
                .discount(orderResponseDTO.getDiscount())
                .grandTotal(orderResponseDTO.getGrandTotal())
                .build();
    }

    @Override
    public OrderResponseDTO makeSimplePayment(String mobile) {
        OrderEntity order = prepareOrder(mobile);

        // Validate cart to make it empty
        Cart cart = validationUtils.validateCart(mobile);
        // Empty the cart
        cart.getFoodList().clear();
        cartRepository.save(cart);

        // Save the order
        orderRepository.save(order);

        // Remove food from cart and add to the order
        List<Food> foodList = order.getFoodList();
        for(Food food : foodList) {
            food.setCart(null);
            food.setOrderEntity(order);
            foodRepository.save(food);
        }

        // Save the order
        orderRepository.save(order);



        return OrderTransformer.OrderToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO makeCouponPayment(String mobile, String couponName) {
        OrderEntity order = addCoupon(mobile, couponName);
        orderRepository.save(order);

        // Validate cart to pass argument and return response
        Cart cart = validationUtils.validateCart(mobile);

        // Remove food from cart and add to the order
        List<Food> foodList = cart.getFoodList();
        for(Food food : foodList) {
            food.setCart(null);
            food.setOrderEntity(order);
            foodRepository.save(food);
        }

        // Save the order
        orderRepository.save(order);

        // Empty the cart
        foodList.clear();
        cartRepository.save(cart);


        return OrderTransformer.OrderToOrderResponseDTO(order);
    }

    public OrderEntity addCoupon(String mobile, String couponName) {
        // Validate cart
        Cart cart = validationUtils.validateCart(mobile);
        double cartTotal = cart.getCartTotal();


        // Validate Coupon
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByName(couponName));
        if(optionalCoupon.isEmpty()) {
            throw new InvalidCouponException("Enter a valid Coupon");
        }
        Coupon coupon = optionalCoupon.get();
        double applicableValue = coupon.getApplicableValue();
        if(cartTotal < applicableValue) {
            throw new InvalidCouponException("Add some more item to apply the coupon");
        }

        // Prepare order
        OrderEntity order = prepareOrder(mobile);
        order.setCoupon(coupon);
        return order;
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

    public OrderResponseDTO prepareOrderResponseDTO(OrderEntity order, Cart cart) {

        Coupon coupon = order.getCoupon();
        // Prepare OrderResponseDTO
        OrderResponseDTO orderResponseDTO = OrderTransformer.OrderToOrderResponseDTO(order);
        orderResponseDTO.setCoupon(coupon.getName());

        // Set Discount and final price
        int discount = calculateDiscount(cart.getCartTotal(), coupon);
        orderResponseDTO.setDiscount(discount);
        orderResponseDTO.setGrandTotal(cart.getCartTotal() - discount);
        return orderResponseDTO;
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
        order.setFoodList(new ArrayList<>(cart.getFoodList()));
        return order;
    }
}
