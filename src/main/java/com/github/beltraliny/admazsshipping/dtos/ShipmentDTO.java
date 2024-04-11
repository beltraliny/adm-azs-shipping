package com.github.beltraliny.admazsshipping.dtos;

public record ShipmentDTO(
        String customer_id,
        AddressDTO origin,
        AddressDTO destination,
        String sendDate,
        String estimatedDeliveryDate,
        String type,
        Double weight,
        Double length,
        Double width,
        Double height,
        Double cubage,
        String declaredValue,
        String transportationType,
        String trackingCode
) {
}
