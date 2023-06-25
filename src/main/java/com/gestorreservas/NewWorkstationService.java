package com.gestorreservas;

import com.gestorreservas.model.AvailabilityStatus;
import com.gestorreservas.model.BookingView;
import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.CategoryView;
import com.gestorreservas.model.FloorView;
import com.gestorreservas.model.NewWorkstationBookingView;
import com.gestorreservas.model.ResourceView;
import com.gestorreservas.model.UserView;
import com.gestorreservas.persistence.BuildingEntity;
import com.gestorreservas.persistence.BuildingRepository;
import com.gestorreservas.persistence.FloorEntity;
import com.gestorreservas.persistence.FloorRepository;
import com.gestorreservas.persistence.QUserEntity;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.BookingEntity;
import com.gestorreservas.persistence.booking.BookingRepository;
import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.persistence.resource.ResourceEntity;
import com.gestorreservas.persistence.resource.ResourceRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NewWorkstationService {

    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public ResourceView getResource(String resourceId) {
        ResourceEntity r = resourceRepository.findById(resourceId).orElseThrow(() -> new IllegalArgumentException("Unable to find resource with id " + resourceId));
        return new ResourceView(r.getId(), r.getName(), r.getFloorId(), CategoryView.valueOf(r.getCategory().name()), AvailabilityStatus.FREE);
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

    public List<UserView> findUsers(String keyword, String organizationId) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(0, 10);

        QUserEntity user = QUserEntity.userEntity;
        keyword = keyword.trim().toLowerCase();

        BooleanExpression booleanExpression = user.organizationId.eq(organizationId)
                .and(user.name.append(" ").concat(user.surname)
                        .containsIgnoreCase(keyword).or(user.email.contains(keyword)));
        return userRepository.findAll(booleanExpression, pageable).map(this::constructUserView).getContent();
    }

    private UserView constructUserView(UserEntity entity) {
        return new UserView(entity.getId(), entity.getName(), entity.getSurname(), entity.getEmail(), entity.getOrganizationId());
    }

    public List<BookingView> getResourceBookings(String resourceId, LocalDateTime start, LocalDateTime end) {
        List<BookingEntity> entities = bookingRepository.getResourceBookingsForRange(resourceId, start, end);

        List<BookingView> bookings = new ArrayList<>();
        for (BookingEntity booking : entities) {
            UserEntity userEntity = userRepository.findById(booking.getOrganizerId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("User with id %s not found", booking.getOrganizerId())));
            UserView userView = new UserView(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getOrganizationId());
            BookingView bookingView = new BookingView(booking.getId(), booking.getResourceId(), booking.getStartDate(), booking.getEndDate(), userView);
            bookingView.setCheckInDate(booking.getCheckInDate());
            bookingView.setCheckOutDate(booking.getCheckOutDate());
            bookings.add(bookingView);
        }

        return bookings;
    }

    public void cancelBookings(List<BookingView> bookings) {
        bookingRepository.deleteAllByIdIn(bookings.stream().map(BookingView::getId).collect(Collectors.toList()));
    }

    public void createWorkstationBooking(NewWorkstationBookingView booking) {
        BookingEntity b = new WorkstationBookingEntity(booking.getOrganizer().getId(), booking.getResource().getId(), booking.getFloor().getId(),
                booking.constructStartDate(), booking.constructEndDate());
        bookingRepository.save(b);
    }
}
