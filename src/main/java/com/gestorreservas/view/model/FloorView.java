package com.gestorreservas.view.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class FloorView {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String buildingId;

    @NonNull
    private Integer floorNumber;
}
