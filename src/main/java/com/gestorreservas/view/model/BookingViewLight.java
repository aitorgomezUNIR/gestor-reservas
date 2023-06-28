package com.gestorreservas.view.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class BookingViewLight {
    @NonNull
    private String id;

    @NonNull
    private String resourceId;

    @NonNull
    private String duration;
}
