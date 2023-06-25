package com.gestorreservas.model;

/**
 *
 * @author Aitor Gómez Afonso
 */
public enum AvailabilityStatus {
    FREE("#3ece8e"), PARTIALLY_OCCUPIED("#ffbc00"), OCCUPIED("#f04343");

    private final String color;

    private AvailabilityStatus(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
