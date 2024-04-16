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

    public Shipment update(String id, ShipmentDTO shipmentDTO) {
        Customer customer = this.customerRepository.findById(shipmentDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Shipment shipmentToBeUpdated = updateShipmentIfNecessary(customer, shipmentDTO, id);
        return this.shipmentRepository.save(shipmentToBeUpdated);
    }

    public void delete(String id, ShipmentDTO shipmentDTO) {
        Customer customer = this.customerRepository.findById(shipmentDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Shipment shipment = this.shipmentRepository.findByCustomerAndId(customer, id).get();
        if (shipment == null) return;

        this.addressService.delete(shipment.getOrigin().getId());
        this.addressService.delete(shipment.getDestination().getId());

        this.shipmentRepository.deleteById(id);
    }

    private Shipment updateShipmentIfNecessary(Customer customer, ShipmentDTO shipmentDTO, String id) {
        Shipment shipment = this.shipmentRepository.findByCustomerAndId(customer, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.addressService.updateAddressIfNecessary(shipment.getOrigin(), shipmentDTO.getOrigin());
        this.addressService.updateAddressIfNecessary(shipment.getDestination(), shipmentDTO.getOrigin());

        if (shipmentDTO.getSendDate() != null) shipment.setSendDate(shipmentDTO.getSendDate());
        if (shipmentDTO.getEstimatedDeliveryDate() != null) shipment.setEstimatedDeliveryDate(shipmentDTO.getEstimatedDeliveryDate());
        if (shipmentDTO.getType() != null) shipment.setType(shipmentDTO.getType());
        if (shipmentDTO.getWeight() != null) shipment.setWeight(shipmentDTO.getWeight());
        if (shipmentDTO.getLength() != null) shipment.setLength(shipmentDTO.getLength());
        if (shipmentDTO.getWidth() != null) shipment.setWidth(shipmentDTO.getWidth());
        if (shipmentDTO.getHeight() != null) shipment.setHeight(shipmentDTO.getHeight());
        if (shipmentDTO.getDeclaredValue() != null) shipment.setDeclaredValue(shipmentDTO.getDeclaredValue());
        if (shipmentDTO.getTransportationType() != null) shipment.setTransportationType(shipmentDTO.getTransportationType());

        return shipment;
    }
}
