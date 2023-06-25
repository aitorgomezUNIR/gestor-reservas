package com.gestorreservas.model;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class ResourceView {

    @NonNull
    private String id;

    @NonNull
    private String name;


    @NonNull
    private String floorId;

    @NonNull
    private CategoryView category;

    @NonNull
    private AvailabilityStatus availabilityStatus;

    private int capacity;

    private List<BookingView> bookings;
}
