package com.gestorreservas.view.model;

import java.time.LocalDateTime;

/**
 *
 * @author Aitor Gómez Afonso
 */
public class WorkstationBookingView extends BookingView {

    public WorkstationBookingView() {
    }


    public WorkstationBookingView(String id, ResourceViewLight resoruce, LocalDateTime start, LocalDateTime end, UserView organizer) {
        super(id, resoruce, start, end, organizer);
    }
}
