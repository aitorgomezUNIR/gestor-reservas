package com.gestorreservas;

import com.gestorreservas.view.model.AvailabilityStatus;
import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.CategoryView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.OccupationLegendView;
import com.gestorreservas.view.model.ResourceView;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.persistence.BuildingEntity;
import com.gestorreservas.persistence.BuildingRepository;
import com.gestorreservas.persistence.FloorEntity;
import com.gestorreservas.persistence.FloorRepository;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.BookingEntity;
import com.gestorreservas.persistence.booking.BookingRepository;
import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.persistence.resource.ResourceEntity;
import com.gestorreservas.persistence.resource.ResourceRepository;
import com.gestorreservas.persistence.resource.SpaceEntity;
import com.gestorreservas.persistence.resource.WorkstationEntity;
import com.gestorreservas.view.model.ResourceViewLight;
import com.gestorreservas.view.model.SpaceBookingView;
import com.gestorreservas.view.model.WorkstationBookingView;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class ResourceListService {

    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<BuildingView> getOrgBuildings(String orgId) {
        // TO-DO: Ordenar los edificios!
        return buildingRepository.findAllByOrganizationId(orgId)
                .stream()
                .map(b -> new BuildingView(b.getId(), b.getName(), b.getOrganizationId()))
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
    }

    public BuildingView getBuilding(String buildingId) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Building with id %s not found", buildingId)));
        return new BuildingView(buildingEntity.getId(), buildingEntity.getName(), buildingEntity.getOrganizationId());
    }

    public List<FloorView> getBuildingFloors(String buildingId) {
        // TO-DO: Ordenar las plantas!
        return floorRepository.findAllByBuildingId(buildingId)
                .stream()
                .map(f -> new FloorView(f.getId(), f.getName(), f.getBuildingId(), f.getFloorNumber()))
                .sorted((o1, o2) -> o1.getFloorNumber().compareTo(o2.getFloorNumber()))
                .collect(Collectors.toList());
    }

    public FloorView getFloor(String floorId) {
        FloorEntity floorEntity = floorRepository.findById(floorId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Floor with id %s not found", floorId)));
        return new FloorView(floorEntity.getId(), floorEntity.getName(), floorEntity.getBuildingId(), floorEntity.getFloorNumber());
    }

    public List<ResourceView> getFloorResources(String floorId, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        List<ResourceEntity> entities = resourceRepository.findAllByFloorId(floorId);
        List<BookingEntity> bookingEntities = bookingRepository.getFloorBookingsForRange(floorId, rangeStart, rangeEnd);
        List<ResourceView> floorResources = new ArrayList<>();

        for (ResourceEntity r : entities) {
            ResourceView resourceView = new ResourceView(r.getId(), r.getName(), r.getFloorId(), CategoryView.valueOf(r.getCategory().name()), AvailabilityStatus.FREE);
            int capacity = r instanceof WorkstationEntity ? 1 : ((SpaceEntity) r).getCapacity();
            resourceView.setCapacity(capacity);
            List<BookingEntity> resourceBookings = bookingEntities.stream().filter(b -> b.getResourceId().equals(r.getId())).collect(Collectors.toList());
            resourceView.setAvailabilityStatus(getAvailabilityStatus(resourceBookings, rangeStart, rangeEnd));
            floorResources.add(resourceView);
        }
        return floorResources;
    }

    public List<BookingView> getResourceBookings(ResourceView resourceView, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        String resourceId = resourceView.getId();
        List<BookingEntity> bookingEntities = bookingRepository.getResourceBookingsForRange(resourceId, rangeStart, rangeEnd);
        List<BookingView> bookings = new ArrayList<>();
        for (BookingEntity booking : bookingEntities) {
            UserEntity userEntity = userRepository.findById(booking.getOrganizerId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("User with id %s not found", booking.getOrganizerId())));
            UserView userView = new UserView(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getOrganizationId());
            ResourceViewLight resource = new ResourceViewLight(resourceId, resourceView.getName(), resourceView.getFloorId(), resourceView.getCategory());
            BookingView bookingView;
            if (booking instanceof WorkstationBookingEntity) {
                bookingView = new WorkstationBookingView(booking.getId(), resource, booking.getStartDate(), booking.getEndDate(), userView);
            } else {
                bookingView = new SpaceBookingView(booking.getId(), resource, booking.getStartDate(), booking.getEndDate(), userView);
            }
            bookingView.setCheckInDate(booking.getCheckInDate());
            bookingView.setCheckOutDate(booking.getCheckOutDate());
            bookings.add(bookingView);
        }
        return bookings;
    }

    public void updateAvailabilityStatus(List<ResourceView> floorResources, String floorId, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        List<BookingEntity> bookingEntities = bookingRepository.getFloorBookingsForRange(floorId, rangeStart, rangeEnd);
        for (ResourceView r : floorResources) {
            List<BookingEntity> resourceBookings = bookingEntities.stream().filter(b -> b.getResourceId().equals(r.getId())).collect(Collectors.toList());
            r.setAvailabilityStatus(getAvailabilityStatus(resourceBookings, rangeStart, rangeEnd));
        }
    }

    private AvailabilityStatus getAvailabilityStatus(List<BookingEntity> bookingEntities, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        long startMs = rangeStart.toInstant(ZoneOffset.UTC).toEpochMilli();
        long endMs = rangeEnd.toInstant(ZoneOffset.UTC).toEpochMilli();
        long rangeMs = endMs - startMs;

        long totalBookingsDuration = 0;

        for (BookingEntity booking : bookingEntities) {
            totalBookingsDuration += booking.getDuration();
        }

        if (totalBookingsDuration == 0) {
            return AvailabilityStatus.FREE;
        } else if (totalBookingsDuration < rangeMs) {
            return AvailabilityStatus.PARTIALLY_OCCUPIED;
        } else {
            return AvailabilityStatus.OCCUPIED;
        }
    }


    public LocalTime roundMinutes(LocalTime time) {
        int minutes = time.getMinute();
        int hour = time.getHour();

        if (minutes > 45) {
            minutes = 0;
            hour++;
            hour = hour == 24 ? 0 : hour;
        } else if (minutes > 30) {
            minutes = 45;
        } else if (minutes > 15) {
            minutes = 30;
        } else if (minutes > 0) {
            minutes = 15;
        }
        return time.withHour(hour).withMinute(minutes).withSecond(0).withNano(0);
    }

    public OccupationLegendView constructOccupationLegend(List<ResourceView> resources) {
        int totalResources = 0;
        int free = 0;
        int occupied = 0;
        int partiallyOccupied = 0;

        for (ResourceView resource : resources) {
            totalResources++;
            switch (resource.getAvailabilityStatus()) {
                case FREE:
                    free++;
                    break;
                case OCCUPIED:
                    occupied++;
                    break;
                case PARTIALLY_OCCUPIED:
                    partiallyOccupied++;
                    break;
                default:
                    throw new IllegalArgumentException("AvailabilityStatus not recognized " + resource.getAvailabilityStatus().name());
            }
        }
        return OccupationLegendView.builder()
                .totalResources(totalResources)
                .free(free)
                .occupied(occupied)
                .partiallyOccupied(partiallyOccupied)
                .build();
    }

}
