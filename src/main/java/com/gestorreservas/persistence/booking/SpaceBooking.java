package com.gestorreservas.persistence.booking;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_space")
public class SpaceBooking extends BookingEntity {

    private String subject;

    private String description;

    public SpaceBooking() {
    }

    public SpaceBooking(String organizerId, String resourceId, String floorId, LocalDateTime startDate, LocalDateTime endDate) {
        super(organizerId, resourceId, floorId, startDate, endDate);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
