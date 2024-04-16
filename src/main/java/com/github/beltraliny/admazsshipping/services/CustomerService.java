package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.CustomerDTO;
import com.github.beltraliny.admazsshipping.exceptions.ValidationException;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.repositories.CustomerRepository;
import com.github.beltraliny.admazsshipping.utils.ValidationUtils;
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

        String[] updatableFields = { "name", "phoneNumber", "email" };
        for (String fieldName : updatableFields) {
            try {
                /*
                    Da lista dos atributos que podem ser atualizados, vamos verificar quais não
                    estão nulos em CustomerDTO e atribuí-los a Customer.
                 */
                Field field = customerDTO.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                var value = field.get(customerDTO);

                if (value != null && !value.toString().trim().isEmpty()) {
                    Field customerField = customer.getClass().getDeclaredField(fieldName);
                    customerField.setAccessible(true);
                    customerField.set(customer, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                throw new ValidationException();
            }
        }

        return customer;
    }

    public void delete(String id) {
        Customer customer = this.customerRepository.findById(id).get();
        if (customer == null) return;

        this.addressService.delete(customer.getAddress().getId());
        this.customerRepository.deleteById(id);
    }

    private void validateBeforeSave(Customer customer) {
        String[] fieldsToValidate = {"cpfCnpj", "email"};
        ValidationUtils.validateEntityBeforeSave(customer, fieldsToValidate);
    }
}
