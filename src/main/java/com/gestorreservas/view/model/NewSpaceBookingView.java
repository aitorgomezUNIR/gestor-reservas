package com.gestorreservas.view.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class NewSpaceBookingView {

    private ResourceViewLight resource;

    private UserView creator;

    private UserView organizer;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private FloorView floor;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private List<NewAttendeeView> attendees;

    @Size(max = 255)
    private String subject;

    @Size(max = 255)
    private String description;

    public NewSpaceBookingView(UserView creator, ResourceViewLight resource, LocalDate date, LocalTime startTime, LocalTime endTime, FloorView floor) {
        this.creator = creator;
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

    public List<String> getAttendeesUserIds() {
        return this.attendees.stream().map(a -> a.getUser().getId()).collect(Collectors.toList());
    }
}
