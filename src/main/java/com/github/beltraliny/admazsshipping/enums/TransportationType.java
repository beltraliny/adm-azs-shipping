package com.github.beltraliny.admazsshipping.enums;

public enum TransportationType {

    AIR,
    MARINE,
    RAIL,
    ROAD;

    public static TransportationType convert(String transportationType) {
        try {
          return TransportationType.valueOf(transportationType);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String convertToString(TransportationType transportationType) {
        try {
            return transportationType.toString();
        } catch (Exception exception) {
            return null;
        }
    }
}
