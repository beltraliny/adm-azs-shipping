package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.CustomerDTO;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    public Customer create(CustomerDTO customerDTO) {
        Address address = this.addressService.create(customerDTO.getAddress());

        Customer customer = new Customer(customerDTO);
        customer.setAddress(address);
        return this.customerRepository.save(customer);
    }

    public Customer findById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
