package com.github.beltraliny.admazsshipping.controllers;

import com.github.beltraliny.admazsshipping.dtos.CustomerDTO;
import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerDTO customerDTO) {
        Customer customer = this.customerService.create(customerDTO);
        return ResponseEntity.created(URI.create("/api/customers/" + customer.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable String id) {
        Customer customer = this.customerService.findById(id);
        return ResponseEntity.ok(new CustomerDTO(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = this.customerService.update(id, customerDTO);
        return ResponseEntity.ok(new CustomerDTO(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
