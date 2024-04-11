package com.github.beltraliny.admazsshipping.dtos;

public record AddressDTO(
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String country,
        String complement,
        String postalCode
) {
}
