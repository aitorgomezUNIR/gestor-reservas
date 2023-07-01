package com.gestorreservas;

import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.persistence.booking.WorkstationBookingRepository;
import com.gestorreservas.view.model.WorkstationBookingView;
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
public class EditWorkstationService {
    private final WorkstationBookingRepository workstationBookingRepository;

    @Transactional
    public void editBooking(WorkstationBookingView bookingView) {
        WorkstationBookingEntity entity = workstationBookingRepository.findById(bookingView.getId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve workstation booking with id " + bookingView.getId()));
        entity.setStartDate(bookingView.getStart());
        entity.setEndDate(bookingView.getEnd());
    }
}
