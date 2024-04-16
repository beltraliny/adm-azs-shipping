package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.AddressDTO;
import com.github.beltraliny.admazsshipping.exceptions.AddressValidationException;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address create(AddressDTO addressDTO) {
        Address address = new Address(addressDTO);
        validateBeforeSave(address);

        return this.addressRepository.save(address);
    }

    public Address updateAddressIfNecessary(Address address, AddressDTO addressDTO) {
        if (addressDTO.street() != null) address.setStreet(addressDTO.street());
        if (addressDTO.number() != null) address.setNumber(addressDTO.number());
        if (addressDTO.neighborhood() != null) address.setNeighborhood(addressDTO.neighborhood());
        if (addressDTO.city() != null) address.setCity(addressDTO.city());
        if (addressDTO.state() != null) address.setState(addressDTO.state());
        if (addressDTO.country() != null) address.setCountry(addressDTO.country());
        if (addressDTO.complement() != null) address.setComplement(addressDTO.complement());
        if (addressDTO.postalCode() != null) address.setPostalCode(addressDTO.postalCode());

        return this.addressRepository.save(address);
    }

    public void delete(String id) {
        this.addressRepository.deleteById(id);
    }

    private void validateBeforeSave(Address address) {
        String[] fieldsToValidate = { "street", "number", "neighborhood",
                "city", "state", "country", "postalCode" };

        try {
            for (String fieldName : fieldsToValidate) {
                // Obtém o atributo da classe.
                Field field = address.getClass().getDeclaredField(fieldName);

                // Permite o acesso ao campo mesmo que seja privado
                field.setAccessible(true);
                var value = field.get(address);

                if (value == null || value.toString().trim().isEmpty()) {
                    throw new AddressValidationException(fieldName + " cannot be null or empty.");
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new AddressValidationException();
        }
    }
}
