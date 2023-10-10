package com.seavol.NoshNow.transformer;

import com.seavol.NoshNow.dto.requestDTO.CustomerRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CustomerResponseDTO;
import com.seavol.NoshNow.model.Customer;

import java.util.ArrayList;

public class CustomerTransformer {

    public static CustomerResponseDTO customerToCustomerResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                .name(customer.getName())
                .gender(customer.getGender())
                .email(customer.getEmail())
                .mobileNo(customer.getMobileNo())
                .address(customer.getAddress())
                .build();
    }

    public static Customer customerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO) {
        return Customer.builder()
                .name(customerRequestDTO.getName())
                .gender(customerRequestDTO.getGender())
                .email(customerRequestDTO.getEmail())
                .mobileNo(customerRequestDTO.getMobileNo())
                .address(customerRequestDTO.getAddress())
                .orderEntityList(new ArrayList<>())
                .build();
    }
}
