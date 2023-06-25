package com.gestorreservas.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public String getFormattedTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();
        String formattedStartTime = dtf.format(startTime);
        String formattedEndTime = dtf.format(endTime);
        return String.format("%s - %s", formattedStartTime, formattedEndTime);
    }
}
