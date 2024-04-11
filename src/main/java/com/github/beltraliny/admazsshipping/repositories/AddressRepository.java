package com.github.beltraliny.admazsshipping.repositories;

import com.github.beltraliny.admazsshipping.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
