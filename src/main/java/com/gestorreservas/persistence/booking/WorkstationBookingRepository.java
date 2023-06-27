package com.gestorreservas.persistence.booking;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aitor
 */
public interface WorkstationBookingRepository extends JpaRepository<WorkstationBookingEntity, String> {

}
