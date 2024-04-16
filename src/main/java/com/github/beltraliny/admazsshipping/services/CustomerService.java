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
        return this.customerRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Customer update(String id, CustomerDTO customerDTO) {
        Customer customer = updateCustomerIfNecessary(id, customerDTO);
        return this.customerRepository.save(customer);
    }

    private Customer updateCustomerIfNecessary(String id, CustomerDTO customerDTO) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.addressService.updateAddressIfNecessary(customer.getAddress(), customerDTO.getAddress());

        if (customerDTO.getName() != null) customer.setName(customerDTO.getName());
        if (customerDTO.getPhoneNumber() != null) customer.setPhoneNumber(customerDTO.getPhoneNumber());
        if (customerDTO.getEmail() != null) customer.setEmail(customerDTO.getEmail());

        return this.customerRepository.save(customer);
    }

    public void delete(String id) {
        Customer customer = this.customerRepository.findById(id).get();
        if (customer == null) return;

        this.addressService.delete(customer.getAddress().getId());
        this.customerRepository.deleteById(id);
    }
}
