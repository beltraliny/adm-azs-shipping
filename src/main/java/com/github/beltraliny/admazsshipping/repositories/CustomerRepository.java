package com.github.beltraliny.admazsshipping.repositories;

import com.github.beltraliny.admazsshipping.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
