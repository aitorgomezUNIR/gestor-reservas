package com.gestorreservas.persistence.booking;

import com.gestorreservas.persistence.BaseEntity;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_booking")
public abstract class BookingEntity extends BaseEntity {

    @NotNull
    private String organizerId;

    @NotNull
    private String resourceId;

    @NotNull
    private String floorId;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private Long duration;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    protected BookingEntity() {
    }

    protected BookingEntity(String organizerId, String resourceId, String floorId, LocalDateTime startDate, LocalDateTime endDate) {
        this.organizerId = organizerId;
        this.resourceId = resourceId;
        this.floorId = floorId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = calculateDuration();
    }

    private Long calculateDuration() {
        long startMs = startDate.toInstant(ZoneOffset.UTC).toEpochMilli();
        long endMs = endDate.toInstant(ZoneOffset.UTC).toEpochMilli();
        return endMs - startMs;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getFloorId() {
        return floorId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getDuration() {
        return duration;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }


}
