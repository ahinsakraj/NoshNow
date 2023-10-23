package com.seavol.NoshNow.controller;

import com.seavol.NoshNow.dto.responseDTO.AppliedCouponResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.OrderResponseDTO;
import com.seavol.NoshNow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/add/coupon")
    public ResponseEntity<?> applyCoupon(@RequestParam("mobile") String mobile, @RequestParam("coupon") String coupon) {
        try {
            AppliedCouponResponseDTO appliedCouponResponseDTO = orderService.applyCoupon(mobile, coupon);
            return new ResponseEntity<>(appliedCouponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/remove/coupon")
    public ResponseEntity<?> removeCoupon(@RequestParam("mobile") String mobile) {
        try {
            AppliedCouponResponseDTO appliedCouponResponseDTO = orderService.removeCoupon(mobile);
            return new ResponseEntity<>(appliedCouponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("place-order")
    public ResponseEntity<?> placeOrder(@RequestParam("mobile") String mobile) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.placeOrder(mobile);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("place-order-with-coupon")
    public ResponseEntity<?> placeOrderWithCoupon(@RequestParam("mobile") String mobile, @RequestParam("coupon") String coupon) {
        try {
            OrderResponseDTO orderResponseDTO = orderService.placeOrderWithCoupon(mobile, coupon);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
