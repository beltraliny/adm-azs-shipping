package com.github.beltraliny.admazsshipping.models;

import com.github.beltraliny.admazsshipping.dtos.CustomerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String cpfCnpj;
    private String  phoneNumber;
    private String email;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Customer(CustomerDTO customerDTO) {
        this.name = customerDTO.getName();
        this.cpfCnpj = customerDTO.getCpfCnpj();
        this.phoneNumber = customerDTO.getPhoneNumber();
        this.email = customerDTO.getEmail();
    }
}
