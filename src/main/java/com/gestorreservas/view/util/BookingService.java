package com.gestorreservas.view.util;

import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.BookingEntity;
import com.gestorreservas.persistence.booking.BookingRepository;
import com.gestorreservas.persistence.booking.WorkstationBookingEntity;
import com.gestorreservas.view.model.ResourceViewLight;
import com.gestorreservas.view.model.SpaceBookingView;
import com.gestorreservas.view.model.WorkstationBookingView;
import java.time.LocalDateTime;
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
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public List<BookingView> getResourceBookings(ResourceViewLight resourceView, LocalDateTime start, LocalDateTime end) {
        String resourceId = resourceView.getId();
        List<BookingEntity> entities = bookingRepository.getResourceBookingsForRange(resourceId, start, end);

        List<BookingView> bookings = new ArrayList<>();
        for (BookingEntity booking : entities) {
            UserEntity userEntity = userRepository.findById(booking.getOrganizerId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("User with id %s not found", booking.getOrganizerId())));

            UserEntity creatorEntity = userRepository.findById(booking.getCreatorId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("User with id %s not found", booking.getOrganizerId())));

            UserView organizer = new UserView(userEntity.getId(), userEntity.getName(), userEntity.getSurname(), userEntity.getEmail(), userEntity.getOrganizationId());
            UserView creator = new UserView(creatorEntity.getId(), creatorEntity.getName(), creatorEntity.getSurname(), creatorEntity.getEmail(), creatorEntity.getOrganizationId());
            BookingView bookingView;
            if (booking instanceof WorkstationBookingEntity) {
                bookingView = new WorkstationBookingView(booking.getId(), resourceView, booking.getStartDate(), booking.getEndDate(), creator, organizer);
            } else {
                bookingView = new SpaceBookingView(booking.getId(), resourceView, booking.getStartDate(), booking.getEndDate(), creator, organizer);
            }

            bookingView.setCheckInDate(booking.getCheckInDate());
            bookingView.setCheckOutDate(booking.getCheckOutDate());
            bookings.add(bookingView);
        }

        return bookings;
    }

    @Transactional
    public void cancelBookings(List<BookingView> bookings) {
        bookingRepository.deleteAllByIdIn(bookings.stream().map(BookingView::getId).collect(Collectors.toList()));
    }

    @Transactional
    public void cancelBooking(BookingView booking) {
        bookingRepository.deleteById(booking.getId());
    }

    @Transactional
    public void checkIn(BookingView booking) {
        LocalDateTime now = LocalDateTime.now();
        booking.setCheckInDate(now);
        bookingRepository.checkIn(booking.getId(), now);
    }

    @Transactional
    public void checkOut(BookingView booking) {
        LocalDateTime now = LocalDateTime.now();
        booking.setCheckOutDate(now);
        bookingRepository.checkOut(booking.getId(), now);
    }
}
