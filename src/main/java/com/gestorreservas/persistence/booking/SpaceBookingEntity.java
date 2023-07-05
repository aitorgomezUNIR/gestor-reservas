package com.gestorreservas.persistence.booking;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_space_booking")
public class SpaceBookingEntity extends BookingEntity {

    @Column(length = 255)
    private String subject;

    @Column(length = 255)
    private String description;

    public SpaceBookingEntity() {
    }

    public SpaceBookingEntity(String creatorId, String organizerId, String resourceId, String floorId, LocalDateTime startDate, LocalDateTime endDate) {
        super(creatorId, organizerId, resourceId, floorId, startDate, endDate);
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
