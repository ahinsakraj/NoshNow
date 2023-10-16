package com.seavol.NoshNow.controller;

import com.seavol.NoshNow.dto.requestDTO.DeliveryPartnerRequestDTO;
import com.seavol.NoshNow.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-partner")
public class DeliveryPartnerController {

    final DeliveryPartnerService deliveryPartnerService;

    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDeliverPartner(@RequestBody DeliveryPartnerRequestDTO deliveryPartnerRequestDTO) {
        String message = deliveryPartnerService.addDeliverPartner(deliveryPartnerRequestDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
