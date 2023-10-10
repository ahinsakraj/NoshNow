package com.seavol.NoshNow.service.impl;

import com.seavol.NoshNow.dto.requestDTO.CustomerRequestDTO;
import com.seavol.NoshNow.dto.responseDTO.CustomerResponseDTO;
import com.seavol.NoshNow.exception.CustomerNotFoundException;
import com.seavol.NoshNow.model.Cart;
import com.seavol.NoshNow.model.Customer;
import com.seavol.NoshNow.repository.CustomerRepository;
import com.seavol.NoshNow.service.CustomerService;
import com.seavol.NoshNow.transformer.CartTransformer;
import com.seavol.NoshNow.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = CustomerTransformer.customerRequestDTOToCustomer(customerRequestDTO);

        Cart cart = CartTransformer.prepareCart();
        customer.setCart(cart);
        cart.setCustomer(customer);

        // Save both Customer and cart
        Customer savedCustomer = customerRepository.save(customer);

        return CustomerTransformer.customerToCustomerResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO findByMobile(String mobile) {
        Customer customer = customerRepository.findByMobileNo(mobile);
        if(customer == null) {
            throw new CustomerNotFoundException("Invalid mobile No");
        } else {
            return CustomerTransformer.customerToCustomerResponseDTO(customer);
        }
    }
}
