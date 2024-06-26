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

    public static String convetToString(CargoType cargoType) {
        try {
            return cargoType.toString();
        } catch (Exception exception) {
            return null;
        }
    }
}
