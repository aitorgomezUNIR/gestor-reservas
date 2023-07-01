package com.gestorreservas.persistence.booking;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author aitor
 */
public interface BookingRepository extends JpaRepository<BookingEntity, String> {
    @Query("FROM BookingEntity b WHERE b.floorId = :floorId AND (b.startDate BETWEEN :rangeStart AND :rangeEnd OR b.endDate between :rangeStart AND :rangeEnd OR (b.startDate <= :rangeStart AND b.endDate >= :rangeEnd))")
    List<BookingEntity> getFloorBookingsForRange(@Param("floorId") String floorId, @Param("rangeStart") LocalDateTime rangeStart, @Param("rangeEnd") LocalDateTime rangeEnd);

    @Query("FROM BookingEntity b WHERE b.resourceId = :resourceId AND (b.startDate BETWEEN :rangeStart AND :rangeEnd OR b.endDate between :rangeEnd AND :rangeEnd OR (b.startDate <= :rangeStart AND b.endDate >= :rangeEnd))")
    List<BookingEntity> getResourceBookingsForRange(@Param("resourceId") String resourceId, @Param("rangeStart") LocalDateTime rangeStart, @Param("rangeEnd") LocalDateTime rangeEnd);

    void deleteAllByIdIn(List<String> ids);

    @Modifying
    @Query("UPDATE BookingEntity b SET b.checkInDate = :checkInDate WHERE b.id = :bookingId")
    void checkIn(@Param("bookingId") String bookingId, @Param("checkInDate") LocalDateTime checkInDate);

    @Modifying
    @Query("UPDATE BookingEntity b SET b.checkOutDate = :checkOutDate WHERE b.id = :bookingId")
    void checkOut(@Param("bookingId") String bookingId, @Param("checkOutDate") LocalDateTime checkOutDate);
}
