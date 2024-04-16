package com.github.beltraliny.admazsshipping.dtos;

import com.github.beltraliny.admazsshipping.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String id;
    private String name;
    private String cpfCnpj;
    private String  phoneNumber;
    private String email;
    private AddressDTO address;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.cpfCnpj = customer.getCpfCnpj();
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.address = new AddressDTO(customer.getAddress());
    }
}
