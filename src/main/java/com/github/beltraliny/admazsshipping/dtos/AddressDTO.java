package com.github.beltraliny.admazsshipping.dtos;

import com.github.beltraliny.admazsshipping.models.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String complement;
    private String postalCode;

    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
        this.state = address.getState();
        this.country = address.getCountry();
        this.complement = address.getComplement();
        this.postalCode = address.getPostalCode();
    }
}
