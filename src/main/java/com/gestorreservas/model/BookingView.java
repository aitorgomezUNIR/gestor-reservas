package com.gestorreservas.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class BookingView {
    @NonNull
    private String id;

    @NonNull
    private String resourceId;

    @NonNull
    private LocalDateTime start;

    @NonNull
    private LocalDateTime end;

    @NonNull
    private UserView organizer;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;
}
