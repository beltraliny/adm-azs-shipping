package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.AddressDTO;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address create(AddressDTO addressDTO) {
        Address address = new Address(addressDTO);
        return this.addressRepository.save(address);
    }
}
