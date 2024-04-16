package com.github.beltraliny.admazsshipping.services;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.dtos.ShipmentSearchRequestDTO;
import com.github.beltraliny.admazsshipping.enums.CargoType;
import com.github.beltraliny.admazsshipping.enums.TransportationType;
import com.github.beltraliny.admazsshipping.exceptions.ValidationException;
import com.github.beltraliny.admazsshipping.models.Address;
import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.repositories.CustomerRepository;
import com.github.beltraliny.admazsshipping.repositories.ShipmentRepository;
import com.github.beltraliny.admazsshipping.repositories.ShipmentSpecification;
import com.github.beltraliny.admazsshipping.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentService {

    private final AddressService addressService;
    private final CustomerRepository customerRepository;
    private final ShipmentRepository shipmentRepository;

    public Shipment create(ShipmentDTO shipmentDTO) {
        Customer customer = this.customerRepository.findById(shipmentDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Address originAddress = this.addressService.create(shipmentDTO.getOrigin());
        Address destinationAddress = this.addressService.create(shipmentDTO.getDestination());

        Shipment shipment = new Shipment(shipmentDTO);
        shipment.setCustomer(customer);
        shipment.setOrigin(originAddress);
        shipment.setDestination(destinationAddress);

        shipment.setTrackingCode(buildTrackingCode());
        shipment.setCubage(calculateCubage(shipmentDTO));

        validateBeforeSave(shipment);

        return this.shipmentRepository.save(shipment);
    }

    public List<Shipment> findAll(ShipmentSearchRequestDTO shipmentSearchRequestDTO) {
        Customer customer = this.customerRepository.findById(shipmentSearchRequestDTO.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Pageable pageable = PageRequest.of(shipmentSearchRequestDTO.page(), shipmentSearchRequestDTO.size());
        Specification<Shipment> specification = ShipmentSpecification.findAllBySearchParamAndCustomer(customer, shipmentSearchRequestDTO.searchParam());
        return this.shipmentRepository.findAll(specification, pageable).getContent();
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
        if (shipmentDTO.getWeight() != null) shipment.setWeight(shipmentDTO.getWeight());
        if (shipmentDTO.getLength() != null) shipment.setLength(shipmentDTO.getLength());
        if (shipmentDTO.getWidth() != null) shipment.setWidth(shipmentDTO.getWidth());
        if (shipmentDTO.getHeight() != null) shipment.setHeight(shipmentDTO.getHeight());
        if (shipmentDTO.getDeclaredValue() != null) shipment.setDeclaredValue(shipmentDTO.getDeclaredValue());

        CargoType cargoType = CargoType.convert(shipmentDTO.getType());
        if (cargoType != null) shipment.setType(cargoType);

        TransportationType transportationType = TransportationType.convert(shipmentDTO.getTransportationType());
        if (transportationType != null) shipment.setTransportationType(transportationType);

        return shipment;
    }

    private String buildTrackingCode() {
        String trackingCode = UUID.randomUUID().toString().replace("-","").substring(0, Shipment.TRACKING_CODE_SIZE).toUpperCase();
        boolean existsShipment = this.shipmentRepository.existsByTrackingCode(trackingCode);

        if (existsShipment) return buildTrackingCode();

        return trackingCode;
    }

    private Double calculateCubage(ShipmentDTO shipmentDTO) {
        if (shipmentDTO.getLength() == null) return null;
        if (shipmentDTO.getWidth() == null) return null;
        if (shipmentDTO.getHeight() == null) return null;

        double cubage = shipmentDTO.getLength() * shipmentDTO.getWidth() * shipmentDTO.getHeight() * Shipment.DEFAULT_CUBAGE_FACTOR;
        return Math.round(cubage * 100.0) / 100.0;
    }

    private void validateBeforeSave(Shipment shipment) {
        String[] fieldsToValidate = {"type", "declaredValue", "transportationType"};
        ValidationUtils.validateEntityBeforeSave(shipment, fieldsToValidate);

        if (shipment.getCubage() == null && shipment.getWeight() == null) {
            throw new ValidationException("It is mandatory to provide the weight or the information " +
                    "necessary to calculate the cubage (length, width, height).");
        }
    }
}
