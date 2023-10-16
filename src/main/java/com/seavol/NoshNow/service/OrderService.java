package com.seavol.NoshNow.service;

import com.seavol.NoshNow.dto.responseDTO.AppliedCouponResponseDTO;
import com.seavol.NoshNow.dto.responseDTO.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO placeOrder(String mobile);
    AppliedCouponResponseDTO applyCoupon(String mobile, String coupon);
    AppliedCouponResponseDTO removeCoupon(String mobile);
    OrderResponseDTO makeSimplePayment(String mobile);
    OrderResponseDTO makeCouponPayment(String mobile, String coupon);
}
