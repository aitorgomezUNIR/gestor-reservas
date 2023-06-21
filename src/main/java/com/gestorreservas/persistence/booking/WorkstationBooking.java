package com.gestorreservas.persistence.booking;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_workstation_booking")
public class WorkstationBooking extends BookingEntity {

    public WorkstationBooking() {
    }

    public WorkstationBooking(String organizerId, String resourceId, String floorId, LocalDateTime startDate, LocalDateTime endDate) {
        super(organizerId, resourceId, floorId, startDate, endDate);
    }

}
