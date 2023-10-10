package com.seavol.NoshNow.controller;

import com.seavol.NoshNow.dto.requestDTO.CustomerRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CustomerResponseDTO;
import com.seavol.NoshNow.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponseDTO> addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO customerResponseDTO = customerService.addCustomer(customerRequestDTO);

        return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/find/mobile{mobile}")
    public ResponseEntity<?> findByMobile(@PathVariable("mobile") String mobile) {
        try {
            CustomerResponseDTO customerResponseDTO = customerService.findByMobile(mobile);
            return  new ResponseEntity<>(customerResponseDTO, HttpStatus.FOUND);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
