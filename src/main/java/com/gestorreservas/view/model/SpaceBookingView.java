package com.gestorreservas.view.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Getter
@Setter
public class SpaceBookingView extends BookingView {

    private String subject;

    private String description;

    private List<AttendeeView> attendees;

    public SpaceBookingView() {
    }


    public SpaceBookingView(String id, ResourceViewLight resoruce, LocalDateTime start, LocalDateTime end, UserView organizer) {
        super(id, resoruce, start, end, organizer);
    }

}
