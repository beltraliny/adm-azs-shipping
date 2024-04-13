package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.dtos.ShipmentSearchRequestDTO;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.repositories.CustomerRepository;
import com.github.beltraliny.admazsshipping.repositories.ShipmentRepository;
import com.github.beltraliny.admazsshipping.repositories.ShipmentSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentService {

    private final AddressService addressService;
    private final ShipmentRepository shipmentRepository;
    private final CustomerRepository customerRepository;

    public Shipment create(ShipmentDTO shipmentDTO) {
        Customer customer = this.customerRepository.findById(shipmentDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Address originAddress = this.addressService.create(shipmentDTO.getOrigin());
        Address destinationAddress = this.addressService.create(shipmentDTO.getDestination());

        Shipment shipment = new Shipment(shipmentDTO);
        shipment.setCustomer(customer);
        shipment.setOrigin(originAddress);
        shipment.setDestination(destinationAddress);

        return this.shipmentRepository.save(shipment);
    }

    public List<Shipment> findAll(ShipmentSearchRequestDTO shipmentSearchRequestDTO) {
        Customer customer = this.customerRepository.findById(shipmentSearchRequestDTO.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Pageable pageable = PageRequest.of(shipmentSearchRequestDTO.page(), shipmentSearchRequestDTO.size());
        Specification<Shipment> specification = ShipmentSpecification.findAllBySearchParamAndCustomer(customer, shipmentSearchRequestDTO.searchParam());
        return shipmentRepository.findAll(specification, pageable).getContent();
    }

    public Shipment findByTrackingCode(String trackingCode) {
        Shipment shipment = this.shipmentRepository.findByTrackingCode(trackingCode)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return shipment;
    }

    public void delete(String id) {
        Shipment shipment = this.shipmentRepository.findById(id).get();
        if (shipment == null) return;

        this.addressService.deleteByShipment(shipment);
        this.shipmentRepository.deleteById(id);
    }
}
