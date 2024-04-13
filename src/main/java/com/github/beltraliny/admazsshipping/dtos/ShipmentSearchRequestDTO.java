package com.github.beltraliny.admazsshipping.dtos;

public record ShipmentSearchRequestDTO(String customerId, String searchParam, int page, int size) {
}
