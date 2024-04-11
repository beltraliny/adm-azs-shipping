package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.repositories.AddressRepository;
import com.github.beltraliny.admazsshipping.repositories.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentService {

    private final AddressService addressService;
    private final ShipmentRepository shipmentRepository;

    public Shipment create(ShipmentDTO shipmentDTO) {
        Address originAddress = this.addressService.create(shipmentDTO.getOrigin());
        Address destinationAddress = this.addressService.create(shipmentDTO.getDestination());

        Shipment shipment = new Shipment(shipmentDTO);
        shipment.setOrigin(originAddress);
        shipment.setDestination(destinationAddress);

        return this.shipmentRepository.save(shipment);
    }

    public Shipment findByTrackingCode(String trackingCode) {
        Shipment shipment = this.shipmentRepository.findByTrackingCode(trackingCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return shipment;
    }
}
