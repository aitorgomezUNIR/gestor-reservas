package com.gestorreservas.view.util;

import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.gestorreservas.persistence.booking.BookingEntity;
import com.gestorreservas.persistence.booking.BookingRepository;
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

    @Transactional
    public void cancelBookings(List<BookingView> bookings) {
        bookingRepository.deleteAllByIdIn(bookings.stream().map(BookingView::getId).collect(Collectors.toList()));
    }
}
