package com.github.beltraliny.admazsshipping.repositories;

import com.github.beltraliny.admazsshipping.models.Customer;
import com.github.beltraliny.admazsshipping.models.Shipment;
import org.springframework.data.jpa.domain.Specification;

public interface ShipmentSpecification extends Specification<Shipment> {

    static Specification<Shipment> findAllBySearchParamAndCustomer(Customer customer, String searchParam) {
        if (searchParam == null || searchParam.isBlank()) {
            return (shipment, query, criteriaBuilder) -> criteriaBuilder.equal(shipment.get("customer"), customer);
        }

        String parsedSearchParam = "%" + searchParam.trim() + "%";
        return (shipment, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("sendDate")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("estimatedDeliveryDate")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("type")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("weight").as(String.class)), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("length").as(String.class)), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("width").as(String.class)), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("height").as(String.class)), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("cubage").as(String.class)), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("declaredValue")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("transportationType")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("trackingCode")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("customer").get("name")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("street")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("neighborhood")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("city")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("state")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("country")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("complement")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("origin").get("postalCode")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("street")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("neighborhood")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("city")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("state")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("country")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("complement")), parsedSearchParam),
                    criteriaBuilder.like(criteriaBuilder.lower(shipment.get("destination").get("postalCode")), parsedSearchParam)
                ), criteriaBuilder.equal(shipment.get("customer"), customer)
        );
    }
}
