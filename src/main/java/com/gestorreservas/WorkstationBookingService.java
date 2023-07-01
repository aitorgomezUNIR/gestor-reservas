package com.gestorreservas;

import com.gestorreservas.view.model.ResourceViewLight;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.view.model.WorkstationBookingView;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.persistence.booking.WorkstationBookingRepository;
import com.gestorreservas.persistence.resource.ResourceEntity;
import com.gestorreservas.persistence.resource.WorkstationRepository;
import com.gestorreservas.view.model.CategoryView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WorkstationBookingService {

    private final WorkstationBookingRepository workstationBookingRepository;
    private final WorkstationRepository workstationRepository;
    private final UserRepository userRepository;

    public WorkstationBookingView getWorkstationBooking(String bookingId) {
        WorkstationBookingEntity entity = workstationBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve workstation booking with id " + bookingId));

        ResourceEntity resourceEntity = workstationRepository.findById(entity.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve workstation with id " + entity.getResourceId()));

        ResourceViewLight resourceView = new ResourceViewLight(resourceEntity.getId(), resourceEntity.getName(), resourceEntity.getFloorId(),
                CategoryView.valueOf(resourceEntity.getCategory().name()));

        UserEntity userEntity = userRepository.findById(entity.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve user with id " + entity.getOrganizerId()));

        UserView organizer = new UserView(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getOrganizationId());

        WorkstationBookingView workstationBookingView = new WorkstationBookingView(bookingId, resourceView, entity.getStartDate(), entity.getEndDate(), organizer);
        workstationBookingView.setCheckInDate(entity.getCheckInDate());
        workstationBookingView.setCheckOutDate(entity.getCheckOutDate());
        return workstationBookingView;
    }
}
