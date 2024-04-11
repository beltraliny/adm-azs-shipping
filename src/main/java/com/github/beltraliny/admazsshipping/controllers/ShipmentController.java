package com.github.beltraliny.admazsshipping.controllers;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<Shipment> create(@RequestBody ShipmentDTO shipmentDTO) {
        Shipment shipment = shipmentService.create(shipmentDTO);
        return ResponseEntity.created(URI.create("/api/shipments/" + shipment.getTrackingCode())).build();
    }

    @GetMapping
    public ResponseEntity<Void> findAll() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{trackingCode}")
    public ResponseEntity<ShipmentDTO> findById(@PathVariable String trackingCode) {
        Shipment shipment = this.shipmentService.findByTrackingCode(trackingCode);
        return ResponseEntity.ok(new ShipmentDTO(shipment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return ResponseEntity.noContent().build();
    }
}
