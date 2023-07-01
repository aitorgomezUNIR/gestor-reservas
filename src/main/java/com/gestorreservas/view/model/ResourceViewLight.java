package com.gestorreservas.view.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class ResourceViewLight {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String floorId;

    @NonNull
    private CategoryView category;
}
