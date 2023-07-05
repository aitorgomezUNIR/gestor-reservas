package com.gestorreservas;

import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.AttendeeEntity;
import com.gestorreservas.persistence.booking.AttendeeRepository;
import com.gestorreservas.persistence.booking.AttendeeTypes;
import com.gestorreservas.persistence.booking.SpaceBookingEntity;
import com.gestorreservas.persistence.booking.SpaceBookingRepository;
import com.gestorreservas.persistence.resource.SpaceEntity;
import com.gestorreservas.persistence.resource.SpaceRepository;
import com.gestorreservas.view.model.AttendeeTypesView;
import com.gestorreservas.view.model.AttendeeView;
import com.gestorreservas.view.model.CategoryView;
import com.gestorreservas.view.model.ResourceViewLight;
import com.gestorreservas.view.model.SpaceBookingView;
import com.gestorreservas.view.model.UserView;
import java.util.ArrayList;
import java.util.List;
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
public class SpaceBookingService {
    private final SpaceBookingRepository spaceBookingRepository;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;
    private final AttendeeRepository attendeeRepository;

    public SpaceBookingView getSpaceBooking(String bookingId) {
        SpaceBookingEntity entity = spaceBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve space booking with id " + bookingId));
        SpaceEntity spaceEntity = spaceRepository.findById(entity.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve space with id " + entity.getResourceId()));
        ResourceViewLight resource = new ResourceViewLight(spaceEntity.getId(), spaceEntity.getName(), spaceEntity.getFloorId(),
                CategoryView.valueOf(spaceEntity.getCategory().name()));

        UserEntity userEntity = userRepository.findById(entity.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve user with id " + entity.getOrganizerId()));

        UserEntity creatorEntity = userRepository.findById(entity.getCreatorId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve user with id " + entity.getOrganizerId()));

        UserView organizer = new UserView(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getOrganizationId());
        UserView creator = new UserView(creatorEntity.getId(), creatorEntity.getName(), creatorEntity.getSurname(), creatorEntity.getEmail(), creatorEntity.getOrganizationId());

        SpaceBookingView spaceBooking = new SpaceBookingView(bookingId, resource, entity.getStartDate(), entity.getEndDate(), creator, organizer);
        spaceBooking.setSubject(entity.getSubject());
        spaceBooking.setDescription(entity.getDescription());
        spaceBooking.setCheckInDate(entity.getCheckInDate());
        spaceBooking.setCheckOutDate(entity.getCheckOutDate());

        List<AttendeeEntity> attendeEntities = attendeeRepository.findAllByBookingId(bookingId);
        List<AttendeeView> attendees = new ArrayList<>();

        for (AttendeeEntity attendee : attendeEntities) {
            UserEntity attendeeUser = userRepository.findById(attendee.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve user with id " + entity.getOrganizerId()));

            UserView user = new UserView(attendeeUser.getId(), attendeeUser.getName(), attendeeUser.getSurname(), attendeeUser.getEmail(), attendeeUser.getOrganizationId());
            AttendeeView attendeeView = new AttendeeView(attendee.getId(), bookingId, user, AttendeeTypesView.valueOf(attendee.getType().name()));
            attendeeView.setOptional(AttendeeTypes.OPTIONAL.equals(attendee.getType()));
            attendees.add(attendeeView);

        }
        spaceBooking.setAttendees(attendees);
        return spaceBooking;
    }
}
