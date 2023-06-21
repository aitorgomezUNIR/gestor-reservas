package com.gestorreservas.persistence.booking;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aitor
 */
public interface BookingRepository extends JpaRepository<BookingEntity, String> {

}
