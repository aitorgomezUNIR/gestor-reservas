package com.gestorreservas.model;

/**
 *
 * @author aitor
 */
public enum CategoryView {
    WORKSTATION("Puesto de trabajo"), SPACE("Sala de reuniones");

    private final String displayName;

    private CategoryView(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
