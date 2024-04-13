package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.AddressDTO;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address create(AddressDTO addressDTO) {
        Address address = new Address(addressDTO);
        return this.addressRepository.save(address);
    }

    public void deleteByShipment(Shipment shipment) {
        this.addressRepository.deleteById(shipment.getOrigin().getId());
        this.addressRepository.deleteById(shipment.getDestination().getId());
    }

    public void updateAddressIfNecessary(String id, AddressDTO addressDTO) {
        Address address = this.addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (addressDTO.street() != null) address.setStreet(addressDTO.street());
        if (addressDTO.number() != null) address.setNumber(addressDTO.number());
        if (addressDTO.neighborhood() != null) address.setNeighborhood(addressDTO.neighborhood());
        if (addressDTO.city() != null) address.setCity(addressDTO.city());
        if (addressDTO.state() != null) address.setState(addressDTO.state());
        if (addressDTO.country() != null) address.setCountry(addressDTO.country());
        if (addressDTO.complement() != null) address.setComplement(addressDTO.complement());
        if (addressDTO.postalCode() != null) address.setPostalCode(addressDTO.postalCode());

        this.addressRepository.save(address);
    }
}
