package com.gestorreservas.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class BuildingView {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String organizationId;

}
