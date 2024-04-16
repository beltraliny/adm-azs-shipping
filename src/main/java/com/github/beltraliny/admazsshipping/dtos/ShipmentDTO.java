package com.github.beltraliny.admazsshipping.dtos;

import com.github.beltraliny.admazsshipping.enums.CargoType;
import com.github.beltraliny.admazsshipping.enums.TransportationType;
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

    private String id;
    private String customerId;
    private String sendDate;
    private String estimatedDeliveryDate;
    private String type;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private Double cubage;
    private String declaredValue;
    private String transportationType;
    private String trackingCode;
    private AddressDTO origin;
    private AddressDTO destination;

    public ShipmentDTO(Shipment shipment) {
        this.id = shipment.getId();
        this.customerId = shipment.getCustomer().getId();
        this.sendDate = shipment.getSendDate();
        this.estimatedDeliveryDate = shipment.getEstimatedDeliveryDate();
        this.type = CargoType.convetToString(shipment.getType());
        this.weight = shipment.getWeight();
        this.length = shipment.getLength();
        this.width = shipment.getWidth();
        this.height = shipment.getHeight();
        this.cubage = shipment.getCubage();
        this.declaredValue = shipment.getDeclaredValue();
        this.transportationType = TransportationType.convertToString(shipment.getTransportationType());
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
