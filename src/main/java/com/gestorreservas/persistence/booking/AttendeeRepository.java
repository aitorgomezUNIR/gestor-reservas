package com.gestorreservas.persistence.booking;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aitor
 */
public interface AttendeeRepository extends JpaRepository<AttendeeEntity, String> {
    List<AttendeeEntity> findAllByBookingIdAndTypeNot(String bookingId, AttendeeTypes type);
}
