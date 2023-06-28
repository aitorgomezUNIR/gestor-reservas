package com.gestorreservas.view.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class NewSpaceBookingView {

    private ResourceView resource;

    private UserView organizer;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private FloorView floor;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private List<NewAttendeeView> attendees;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    public NewSpaceBookingView(ResourceView resource, LocalDate date, LocalTime startTime, LocalTime endTime, FloorView floor) {
        this.resource = resource;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.floor = floor;
        this.attendees = new ArrayList<>();
    }

    public LocalDateTime constructStartDate() {
        return LocalDateTime.of(date, startTime);
    }

    public LocalDateTime constructEndDate() {
        return LocalDateTime.of(date, endTime);
    }

    public void addNewAttendee(UserView user) {
        this.attendees.add(new NewAttendeeView(user));
    }

    public void removeAttendee(NewAttendeeView newAttendeeView) {
        this.attendees.remove(newAttendeeView);
    }

}
