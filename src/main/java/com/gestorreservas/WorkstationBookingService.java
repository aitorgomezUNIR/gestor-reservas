package com.gestorreservas;

import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.FloorView;
import com.gestorreservas.model.ResourceViewLight;
import com.gestorreservas.model.UserView;
import com.gestorreservas.model.WorkstationBookingView;
import com.gestorreservas.persistence.BuildingEntity;
import com.gestorreservas.persistence.BuildingRepository;
import com.gestorreservas.persistence.FloorEntity;
import com.gestorreservas.persistence.FloorRepository;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.persistence.booking.WorkstationBookingRepository;
import com.gestorreservas.persistence.resource.ResourceEntity;
import com.gestorreservas.persistence.resource.WorkstationRepository;
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
    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final WorkstationRepository workstationRepository;
    private final UserRepository userRepository;

    public WorkstationBookingView getWorkstationBooking(String bookingId) {
        WorkstationBookingEntity entity = workstationBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve workstation booking with id " + bookingId));

        ResourceEntity resourceEntity = workstationRepository.findById(entity.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve workstation with id " + entity.getResourceId()));

        ResourceViewLight resourceView = new ResourceViewLight(resourceEntity.getId(), resourceEntity.getName(), resourceEntity.getFloorId());

        UserEntity userEntity = userRepository.findById(entity.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve user with id " + entity.getOrganizerId()));

        UserView organizer = new UserView(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getOrganizationId());

        WorkstationBookingView workstationBookingView = new WorkstationBookingView(bookingId, resourceView,
                entity.getStartDate().toLocalDate(), entity.getStartDate().toLocalTime(), entity.getEndDate().toLocalTime(), organizer);
        workstationBookingView.setCheckInDate(entity.getCheckInDate());
        workstationBookingView.setCheckOutDate(entity.getCheckOutDate());
        return workstationBookingView;
    }

    public BuildingView getBuilding(String buildingId) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Building with id %s not found", buildingId)));
        return new BuildingView(buildingEntity.getId(), buildingEntity.getName(), buildingEntity.getOrganizationId());
    }

    public FloorView getFloor(String floorId) {
        FloorEntity floorEntity = floorRepository.findById(floorId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Floor with id %s not found", floorId)));
        return new FloorView(floorEntity.getId(), floorEntity.getName(), floorEntity.getBuildingId(), floorEntity.getFloorNumber());
    }
}
