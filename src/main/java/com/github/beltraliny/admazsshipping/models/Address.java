package com.github.beltraliny.admazsshipping.models;

import com.github.beltraliny.admazsshipping.dtos.AddressDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String complement;
    private String postalCode;

    public Address(AddressDTO addressDTO) {
        this.street = addressDTO.street();
        this.number = addressDTO.number();
        this.neighborhood = addressDTO.neighborhood();
        this.city = addressDTO.city();
        this.state = addressDTO.state();
        this.country = addressDTO.country();
        this.complement = addressDTO.complement();
        this.postalCode = addressDTO.postalCode();
    }
}
