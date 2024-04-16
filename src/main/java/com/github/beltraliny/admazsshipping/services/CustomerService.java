package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.CustomerDTO;
import com.github.beltraliny.admazsshipping.exceptions.CustomerValidationException;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final AddressService addressService;
    private final CustomerRepository customerRepository;

    public Customer create(CustomerDTO customerDTO) {
        Address address = this.addressService.create(customerDTO.getAddress());

        Customer customer = new Customer(customerDTO);
        customer.setAddress(address);
        validateBeforeSave(customer);

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

    private void validateBeforeSave(Customer customer) {
        String[] fieldsToValidate = { "cpfCnpj", "email" };

        try {
            for (String fieldName : fieldsToValidate) {
                // Obtém o atributo da classe.
                Field field = customer.getClass().getDeclaredField(fieldName);

                // Permite o acesso ao campo mesmo que seja privado
                field.setAccessible(true);
                var value = field.get(customer);

                if (value == null || value.toString().trim().isEmpty()) {
                    throw new CustomerValidationException(fieldName + " cannot be null or empty.");
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new CustomerValidationException();
        }
    }
}
