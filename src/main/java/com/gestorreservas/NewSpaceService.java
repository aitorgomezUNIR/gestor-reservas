package com.gestorreservas;

import com.gestorreservas.view.model.NewAttendeeView;
import com.gestorreservas.view.model.NewSpaceBookingView;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.persistence.booking.AttendeeEntity;
import com.gestorreservas.persistence.booking.AttendeeRepository;
import com.gestorreservas.persistence.booking.AttendeeTypes;
import com.gestorreservas.persistence.booking.SpaceBookingEntity;
import com.gestorreservas.persistence.booking.SpaceBookingRepository;
import com.gestorreservas.persistence.resource.SpaceEntity;
import com.gestorreservas.persistence.resource.SpaceRepository;
import com.gestorreservas.view.model.CategoryView;
import com.gestorreservas.view.model.ResourceViewLight;
import java.util.ArrayList;
import java.util.List;
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
public class NewSpaceService {

    private final SpaceRepository spaceRepository;
    private final SpaceBookingRepository spaceBookingRepository;
    private final AttendeeRepository attendeeRepository;


    @Transactional
    public String createSpaceBooking(NewSpaceBookingView newBooking) {
        UserView organizer = newBooking.getOrganizer();
        SpaceBookingEntity b = new SpaceBookingEntity(organizer.getId(), newBooking.getResource().getId(),
                newBooking.getFloor().getId(), newBooking.constructStartDate(), newBooking.constructEndDate());
        b.setSubject(newBooking.getSubject());
        b.setDescription(newBooking.getDescription());
        b = spaceBookingRepository.save(b);
        List<AttendeeEntity> attendees = new ArrayList<>();
        for (NewAttendeeView a : newBooking.getAttendees()) {
            AttendeeTypes type = a.isOptional() ? AttendeeTypes.OPTIONAL : AttendeeTypes.REQUIRED;
            attendees.add(new AttendeeEntity(a.getUser().getId(), b.getId(), type));
        }

        attendees.add(new AttendeeEntity(organizer.getId(), b.getId(), AttendeeTypes.ORGANIZER));
        attendeeRepository.saveAll(attendees);
        return b.getId();
    }

    public ResourceViewLight getSpace(String resourceId) {
        SpaceEntity r = spaceRepository.findById(resourceId).orElseThrow(() -> new IllegalArgumentException("Unable to find workstation with id " + resourceId));
        return new ResourceViewLight(r.getId(), r.getName(), r.getFloorId(), CategoryView.valueOf(r.getCategory().name()));
    }

}
