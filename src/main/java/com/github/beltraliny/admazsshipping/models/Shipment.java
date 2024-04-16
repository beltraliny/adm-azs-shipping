package com.github.beltraliny.admazsshipping.models;

import com.github.beltraliny.admazsshipping.dtos.ShipmentDTO;
import com.github.beltraliny.admazsshipping.enums.TransportationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shipment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

    public static final int TRACKING_CODE_SIZE = 13;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "origin_id")
    private Address origin;

    @OneToOne
    @JoinColumn(name = "destination_id")
    private Address destination;

    private String sendDate;
    private String estimatedDeliveryDate;
    private String type;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private Double cubage;
    private String declaredValue;
    private TransportationType transportationType;
    private String trackingCode;

    public Shipment(ShipmentDTO shipmentDTO) {
        this.sendDate = shipmentDTO.getSendDate();
        this.estimatedDeliveryDate = shipmentDTO.getEstimatedDeliveryDate();
        this.type = shipmentDTO.getType();
        this.weight = shipmentDTO.getWeight();
        this.length = shipmentDTO.getLength();
        this.width = shipmentDTO.getWidth();
        this.height = shipmentDTO.getHeight();
        this.cubage = shipmentDTO.getCubage();
        this.declaredValue = shipmentDTO.getDeclaredValue();
        this.transportationType = TransportationType.convert(shipmentDTO.getTransportationType());
    }
}
