package com.gestorreservas.view.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Getter
public abstract class BookingView {
    @NonNull
    private String id;

    private ResourceViewLight resource;

    @Setter
    private LocalDateTime start;

    @Setter
    private LocalDateTime end;

    private UserView organizer;

    @Setter
    private LocalDateTime checkInDate;

    @Setter
    private LocalDateTime checkOutDate;

    public BookingView() {
    }


    public BookingView(String id, ResourceViewLight resource, LocalDateTime start, LocalDateTime end, UserView organizer) {
        this.id = id;
        this.resource = resource;
        this.start = start;
        this.end = end;
        this.organizer = organizer;
    }


    public String getFormattedTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = start.toLocalTime();
        LocalTime endTime = end.toLocalTime();
        String formattedStartTime = dtf.format(startTime);
        String formattedEndTime = dtf.format(endTime);
        return String.format("%s - %s", formattedStartTime, formattedEndTime);
    }

    public LocalDate getDate() {
        return start.toLocalDate();
    }

    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    public LocalTime getEndTime() {
        return end.toLocalTime();
    }
}
