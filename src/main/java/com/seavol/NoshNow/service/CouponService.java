package com.seavol.NoshNow.service;

import com.seavol.NoshNow.Enum.CouponType;
import com.seavol.NoshNow.dto.requestDTO.CouponRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CouponResponseDTO;

public interface CouponService {
    CouponResponseDTO addCoupon(CouponRequestDTO couponRequestDTO, CouponType couponType, int value);

    CouponResponseDTO changeExpiryStatus(String name);

    CouponResponseDTO getCoupon(String name);
}
