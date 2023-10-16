package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.Enum.CouponType;
import com.seavol.NoshNow.dto.requestDTO.CouponRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CouponResponseDTO;
import com.seavol.NoshNow.model.Coupon;

import java.util.ArrayList;

public class CouponTransformer {

    public static Coupon CouponRequestDTOToCoupon(CouponRequestDTO couponRequestDTO) {
        return Coupon.builder()
                .name(couponRequestDTO.getName())
                .applicableValue(couponRequestDTO.getApplicableValue())
                .expired(false)
                .flatOff(0)
                .percentOff(0)
                .cashback(0)
                .expired(false)
                .build();
    }

    public static CouponResponseDTO CouponToCouponResponseDTO(Coupon coupon) {
        return CouponResponseDTO.builder()
                .name(coupon.getName())
                .applicableValue(coupon.getApplicableValue())
                .couponType(coupon.getCouponType())
                .expired(coupon.isExpired())
                .build();
    }
}
