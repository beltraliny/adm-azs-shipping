package com.github.beltraliny.admazsshipping.dtos;

import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Shipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    
    String customer_id;
    String sendDate;
    String estimatedDeliveryDate;
    String type;
    Double weight;
    Double length;
    Double width;
    Double height;
    Double cubage;
    String declaredValue;
    String transportationType;
    String trackingCode;
    AddressDTO origin;
    AddressDTO destination;

    public ShipmentDTO(Shipment shipment) {
        this.customer_id = shipment.getCustomer();
        this.sendDate = shipment.getSendDate();
        this.estimatedDeliveryDate = shipment.getEstimatedDeliveryDate();
        this.type = shipment.getType();
        this.weight = shipment.getWeight();
        this.length = shipment.getLength();
        this.width = shipment.getWidth();
        this.height = shipment.getHeight();
        this.cubage = shipment.getCubage();
        this.declaredValue = shipment.getDeclaredValue();
        this.transportationType = shipment.getTransportationType();
        this.trackingCode = shipment.getTrackingCode();


        this.origin = buildAddressData(shipment.getOrigin());
        this.destination = buildAddressData(shipment.getDestination());
    }

    private AddressDTO buildAddressData(Address address) {
        return new AddressDTO(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getComplement(),
                address.getPostalCode()
        );
    }
}
