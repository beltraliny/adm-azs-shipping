package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.repositories.AddressRepository;
import com.github.beltraliny.admazsshipping.repositories.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final AddressRepository addressRepository;
    private final ShipmentRepository shipmentRepository;

    public Shipment create(ShipmentDTO shipmentDTO) {
        Address originAddress = new Address(shipmentDTO.getOrigin());
        this.addressRepository.save(originAddress);

        Address destinationAddress = new Address(shipmentDTO.getDestination());
        this.addressRepository.save(destinationAddress);

        Shipment shipment = new Shipment(shipmentDTO, originAddress, destinationAddress);

        return this.shipmentRepository.save(shipment);
    }

    public Shipment findByTrackingCode(String trackingCode) {
        Shipment shipment = this.shipmentRepository.findByTrackingCode(trackingCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return shipment;
    }
}
