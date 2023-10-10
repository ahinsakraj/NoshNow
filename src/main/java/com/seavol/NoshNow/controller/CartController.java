package com.seavol.NoshNow.controller;

import com.seavol.NoshNow.dto.responseDTO.CartResponseDTO;
import com.seavol.NoshNow.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {

    final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam String mobile, int ItemId) {
        try {
            CartResponseDTO cartResponseDTO = cartService.modifyCart(mobile, ItemId, true);
            return new ResponseEntity<>(cartResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam String mobile, int ItemId) {
        try {
            CartResponseDTO cartResponseDTO = cartService.modifyCart(mobile, ItemId, false);
            return new ResponseEntity<>(cartResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public ResponseEntity<?> getCart(@RequestParam String mobileNo) {
        try {
            CartResponseDTO cartResponseDTO = cartService.getCart(mobileNo);
            return new ResponseEntity<>(cartResponseDTO, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
