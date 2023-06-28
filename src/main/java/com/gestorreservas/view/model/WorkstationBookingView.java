package com.gestorreservas.view.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class WorkstationBookingView {
    @NonNull
    private String id;

    @NonNull
    private ResourceViewLight resource;

    @NonNull
    private LocalDate date;

    @NonNull
    private LocalTime startTime;

    @NonNull
    private LocalTime endTime;

    @NonNull
    private UserView organizer;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;
}
