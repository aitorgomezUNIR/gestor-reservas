package com.gestorreservas;

import com.gestorreservas.view.model.NewWorkstationBookingView;
import com.gestorreservas.persistence.booking.BookingEntity;
import com.gestorreservas.persistence.booking.BookingRepository;
import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.persistence.resource.WorkstationEntity;
import com.gestorreservas.persistence.resource.WorkstationRepository;
import com.gestorreservas.view.model.AvailabilityStatus;
import com.gestorreservas.view.model.CategoryView;
import com.gestorreservas.view.model.ResourceView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NewWorkstationService {

    private final BookingRepository bookingRepository;
    private final WorkstationRepository workstationRepository;

    @Transactional
    public String createWorkstationBooking(NewWorkstationBookingView booking) {
        BookingEntity b = new WorkstationBookingEntity(booking.getOrganizer().getId(), booking.getResource().getId(), booking.getFloor().getId(),
                booking.constructStartDate(), booking.constructEndDate());
        b = bookingRepository.save(b);
        return b.getId();
    }

    public ResourceView getWorkstation(String resourceId) {
        WorkstationEntity r = workstationRepository.findById(resourceId).orElseThrow(() -> new IllegalArgumentException("Unable to find workstation with id " + resourceId));
        return new ResourceView(r.getId(), r.getName(), r.getFloorId(), CategoryView.valueOf(r.getCategory().name()), AvailabilityStatus.FREE);
    }
}
