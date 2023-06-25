package com.gestorreservas.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class NewWorkstationBookingView {
    private ResourceView resource;

    private UserView organizer;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private FloorView floor;

    public NewWorkstationBookingView() {
    }

    public NewWorkstationBookingView(ResourceView resource, LocalDate date, LocalTime startTime, LocalTime endTime, FloorView floor) {
        this.resource = resource;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.floor = floor;
    }

    public LocalDateTime constructStartDate() {
        return LocalDateTime.of(date, startTime);
    }

    public LocalDateTime constructEndDate() {
        return LocalDateTime.of(date, endTime);
    }


}
