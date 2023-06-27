package com.gestorreservas.persistence.booking;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aitor
 */
public interface SpaceBookingRepository extends JpaRepository<SpaceBookingEntity, String> {

}
