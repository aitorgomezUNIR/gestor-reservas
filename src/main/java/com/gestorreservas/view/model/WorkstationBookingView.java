package com.gestorreservas.view.model;

import java.time.LocalDateTime;

/**
 *
 * @author Aitor Gómez Afonso
 */
public class WorkstationBookingView extends BookingView {

    public WorkstationBookingView() {
    }


    public WorkstationBookingView(String id, ResourceViewLight resource, LocalDateTime start, LocalDateTime end, UserView creator, UserView organizer) {
        super(id, resource, start, end, creator, organizer);
    }
}
