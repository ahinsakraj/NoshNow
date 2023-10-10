package com.seavol.NoshNow.service;

import com.seavol.NoshNow.dto.requestDTO.CustomerRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CustomerResponseDTO;
import org.springframework.stereotype.Service;

public interface CustomerService {
    CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO findByMobile(String mobile);

}
