package com.github.beltraliny.admazsshipping.enums;

public enum CargoType {

    LIQUID,
    SOLID,
    GAS,
    PERISHABLE,
    HAZARDOUS,
    BULK,
    CONTAINERIZED;

    public static CargoType convert(String cargoType) {
        try {
            return CargoType.valueOf(cargoType);
        } catch (Exception exception) {
            return null;
        }
    }
}
