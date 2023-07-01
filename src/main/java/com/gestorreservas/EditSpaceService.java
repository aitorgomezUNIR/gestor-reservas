package com.gestorreservas;

import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.AttendeeEntity;
import com.gestorreservas.persistence.booking.AttendeeRepository;
import com.gestorreservas.persistence.booking.AttendeeTypes;
import com.gestorreservas.persistence.booking.SpaceBookingEntity;
import com.gestorreservas.persistence.booking.SpaceBookingRepository;
import com.gestorreservas.view.model.AttendeeTypesView;
import com.gestorreservas.view.model.AttendeeView;
import com.gestorreservas.view.model.SpaceBookingView;
import com.gestorreservas.view.model.UserView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class EditSpaceService {
    private final SpaceBookingRepository spaceBookingRepository;
    private final AttendeeRepository attendeeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void editBooking(SpaceBookingView bookingView) {
        SpaceBookingEntity entity = spaceBookingRepository.findById(bookingView.getId())
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve space booking with id " + bookingView.getId()));
        entity.setStartDate(bookingView.getStart());
        entity.setEndDate(bookingView.getEnd());
        entity.setSubject(bookingView.getSubject());
        entity.setDescription(bookingView.getDescription());

        attendeeRepository.deleteAllByBookingIdAndTypeNot(bookingView.getId(), AttendeeTypes.ORGANIZER);

        List<AttendeeEntity> attendeeEntities = bookingView.getAttendees()
                .stream()
                .map(a -> new AttendeeEntity(a.getUser().getId(), bookingView.getId(), AttendeeTypes.valueOf(a.getType().name())))
                .collect(Collectors.toList());

        attendeeEntities = attendeeRepository.saveAll(attendeeEntities);
        List<AttendeeView> attendees = new ArrayList<>();
        for (AttendeeEntity attendee : attendeeEntities) {
            UserEntity attendeeUser = userRepository.findById(attendee.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve user with id " + entity.getOrganizerId()));

            UserView user = new UserView(attendeeUser.getId(), attendeeUser.getName(), attendeeUser.getSurname(), attendeeUser.getEmail(), attendeeUser.getOrganizationId());
            attendees.add(new AttendeeView(attendee.getId(), bookingView.getId(), user, AttendeeTypesView.valueOf(attendee.getType().name())));

        }
        bookingView.setAttendees(attendees);
    }
}
