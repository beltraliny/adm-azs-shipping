package com.github.beltraliny.admazsshipping.repositories;

import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    Optional<Shipment> findByTrackingCode(String trackingCode);

    List<Shipment> findByCustomer(Customer customer);
}
