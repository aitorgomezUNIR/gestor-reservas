package com.gestorreservas.persistence.booking;

import com.gestorreservas.persistence.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_attendee")
public class AttendeeEntity extends BaseEntity {

    @NotNull
    private String userId;

    @NotNull
    private String bookingId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AttendeeTypes type;

    public AttendeeEntity() {
    }

    public AttendeeEntity(String userId, String bookingId, AttendeeTypes type) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public AttendeeTypes getType() {
        return type;
    }

    public void setType(AttendeeTypes type) {
        this.type = type;
    }

}
