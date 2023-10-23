package com.seavol.NoshNow.service;

import com.seavol.NoshNow.dto.responseDTO.AppliedCouponResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.OrderResponseDTO;

public interface OrderService {
    AppliedCouponResponseDTO applyCoupon(String mobile, String coupon);
    AppliedCouponResponseDTO removeCoupon(String mobile);
    OrderResponseDTO placeOrder(String mobile);
    OrderResponseDTO placeOrderWithCoupon(String mobile, String coupon);
}
