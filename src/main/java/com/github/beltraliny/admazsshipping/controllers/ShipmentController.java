package com.github.beltraliny.admazsshipping.controllers;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.dtos.ShipmentSearchRequestDTO;
import com.github.beltraliny.admazsshipping.models.Shipment;
import com.github.beltraliny.admazsshipping.services.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<Shipment> create(@RequestBody ShipmentDTO shipmentDTO) {
        Shipment shipment = shipmentService.create(shipmentDTO);
        return ResponseEntity.created(URI.create("/api/shipments/" + shipment.getTrackingCode())).build();
    }

    @GetMapping
    public ResponseEntity<List<ShipmentDTO>> findAll(@RequestBody ShipmentSearchRequestDTO shipmentSearchRequestDTO) {
        List<Shipment> shipmentList = this.shipmentService.findAll(shipmentSearchRequestDTO);
        List<ShipmentDTO> shipmentDTOList = shipmentList.stream().map(shipment -> new ShipmentDTO(shipment)).toList();
        return ResponseEntity.ok(shipmentDTOList);
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
        this.shipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
