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
        if (addressDTO == null) return null;

        Field[] dtoFieldList = addressDTO.getClass().getDeclaredFields();

        for (Field dtoField : dtoFieldList) {
            try {
                //Vamos verificar cada atributo de AddressDTO e caso não seja nulo, vamos setar em Address.
                dtoField.setAccessible(true);
                var value = dtoField.get(addressDTO);
                if (value != null && !value.toString().trim().isEmpty()) {
                    Field addressField = address.getClass().getDeclaredField(dtoField.getName());
                    addressField.setAccessible(true);
                    addressField.set(address, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                throw new AddressValidationException();
            }
        }

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
