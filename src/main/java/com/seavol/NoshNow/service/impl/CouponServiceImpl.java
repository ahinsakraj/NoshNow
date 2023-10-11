package com.seavol.NoshNow.service.impl;

import com.seavol.NoshNow.Enum.CouponType;
import com.seavol.NoshNow.dto.requestDTO.CouponRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CouponResponseDTO;
import com.seavol.NoshNow.exception.InvalidCouponException;
import com.seavol.NoshNow.model.Coupon;
import com.seavol.NoshNow.repository.CouponRepository;
import com.seavol.NoshNow.service.CouponService;
import com.seavol.NoshNow.transformer.CouponTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {
    final CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public CouponResponseDTO addCoupon(CouponRequestDTO couponRequestDTO, CouponType couponType, int value) {
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByName(couponRequestDTO.getName()));
        if(optionalCoupon.isPresent()) {
            throw new InvalidCouponException("Coupon already exists.");
        }
        Coupon coupon = CouponTransformer.CouponRequestDTOToCoupon(couponRequestDTO);
        coupon.setCouponType(couponType);
        if(coupon.getCouponType() == CouponType.FLAT_OFF) {
            coupon.setFlatOff(value);
        } else if(coupon.getCouponType() == CouponType.PERCENTAGE_OFF) {
            coupon.setPercentOff(value);
        } else {
            coupon.setCashback(value);
        }
        Coupon savedCoupon = couponRepository.save(coupon);
        CouponResponseDTO couponResponseDTO = CouponTransformer.CouponToCouponResponseDTO(coupon);
        couponResponseDTO.setMessage(getMessage(savedCoupon));
        return couponResponseDTO;
    }

    @Override
    public CouponResponseDTO changeExpiryStatus(String name) {
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByName(name));
        if(optionalCoupon.isEmpty()) {
            throw new InvalidCouponException("Enter a valid coupon");
        }
        Coupon coupon = optionalCoupon.get();
        coupon.setExpired(!coupon.isExpired());
        CouponResponseDTO couponResponseDTO = CouponTransformer.CouponToCouponResponseDTO(coupon);
        couponResponseDTO.setMessage(getMessage(coupon));
        return couponResponseDTO;
    }

    @Override
    public CouponResponseDTO getCoupon(String name) {
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByName(name));
        if(optionalCoupon.isEmpty()) {
            throw new InvalidCouponException("Enter a valid coupon");
        }
        Coupon coupon = optionalCoupon.get();
        CouponResponseDTO couponResponseDTO = CouponTransformer.CouponToCouponResponseDTO(coupon);
        couponResponseDTO.setMessage(getMessage(coupon));
        return couponResponseDTO;
    }

    private String getMessage(Coupon coupon) {
        String message = "";
        if(coupon.getCouponType() == CouponType.FLAT_OFF) {
            message += "Flat off on this coupon : " + coupon.getFlatOff();
        } else if(coupon.getCouponType() == CouponType.PERCENTAGE_OFF) {
            message += "Percentage off on this coupon : " + coupon.getPercentOff();
        } else{
            message += "Cashback on this coupon : " + coupon.getCashback();
        }
        return message;
    }
}
