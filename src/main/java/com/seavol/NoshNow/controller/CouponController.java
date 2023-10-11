package com.seavol.NoshNow.controller;

import com.seavol.NoshNow.Enum.CouponType;
import com.seavol.NoshNow.dto.requestDTO.CouponRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CouponResponseDTO;
import com.seavol.NoshNow.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    final CouponService couponService;
    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/add/flat-off/{flatOff}")
    public ResponseEntity<?> addFlatOff(@RequestBody CouponRequestDTO couponRequestDTO, @PathVariable("flatOff") int flatOff) {
        try {
            CouponResponseDTO couponResponseDTO = couponService.addCoupon(couponRequestDTO, CouponType.FLAT_OFF, flatOff);
            return new ResponseEntity<>(couponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/percentage-off/{percentageOff}")
    public ResponseEntity<?> addPercentageOff(@RequestBody CouponRequestDTO couponRequestDTO, @PathVariable("percentageOff") int percentageOff) {
        try {
            CouponResponseDTO couponResponseDTO = couponService.addCoupon(couponRequestDTO, CouponType.PERCENTAGE_OFF, percentageOff);
            return new ResponseEntity<>(couponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/cashback/{cashback}")
    public ResponseEntity<?> addCashback(@RequestBody CouponRequestDTO couponRequestDTO, @PathVariable("cashback") int cashback) {
        try {
            CouponResponseDTO couponResponseDTO = couponService.addCoupon(couponRequestDTO, CouponType.CASHBACK, cashback);
            return new ResponseEntity<>(couponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-expiry-status/name/{name}")
    public ResponseEntity<?> changeExpiryStatus(@PathVariable("name") String name) {
        try {
            CouponResponseDTO couponResponseDTO = couponService.changeExpiryStatus(name);
            return new ResponseEntity<>(couponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-coupon/name/{name}")
    public ResponseEntity<?> getCoupon(@PathVariable("name") String name) {
        try {
            CouponResponseDTO couponResponseDTO = couponService.getCoupon(name);
            return new ResponseEntity<>(couponResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
