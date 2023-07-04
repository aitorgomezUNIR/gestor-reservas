package com.gestorreservas;

import com.gestorreservas.session.SessionBean;
import com.gestorreservas.view.model.AttendeeTypesView;
import com.gestorreservas.view.model.AttendeeView;
import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.SpaceBookingView;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.view.requestparam.RequestParam;
import com.gestorreservas.view.util.BookingService;
import com.gestorreservas.view.util.ResourceService;
import com.gestorreservas.view.util.UserService;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Component
@ViewScoped
@Slf4j
public class EditSpaceBean implements Serializable {

    private final SessionBean sessionBean;
    private final SpaceBookingService spaceBookingService;
    private final EditSpaceService editSpaceService;
    private final ResourceService resourceService;
    private final BookingService bookingService;
    private final UserService userService;

    @Getter
    private SpaceBookingView spaceBooking;

    @Getter
    private BuildingView building;

    @Getter
    private FloorView floor;

    @Getter
    private List<BookingView> conflictiveBookings;

    @Setter
    @Getter
    private LocalDate date;

    @Setter
    @Getter
    private LocalTime startTime;

    @Setter
    @Getter
    private LocalTime endTime;

    @Getter
    @Setter
    private UserView user;

    public EditSpaceBean(SessionBean sessionBean, SpaceBookingService spaceBookingService, EditSpaceService editSpaceService,
            ResourceService resourceService, BookingService bookingService, UserService userService,
            @RequestParam String bookingId) {
        this.sessionBean = sessionBean;
        this.spaceBookingService = spaceBookingService;
        this.editSpaceService = editSpaceService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        this.userService = userService;
        this.processParams(bookingId);
    }


    private void processParams(String bookingId) {
        if (StringUtils.isBlank(bookingId)) {
            redirectBack();
        }

        try {
            this.spaceBooking = spaceBookingService.getSpaceBooking(bookingId);
            this.floor = resourceService.getFloor(this.spaceBooking.getResource().getFloorId());
            this.building = resourceService.getBuilding(this.floor.getBuildingId());
            this.date = spaceBooking.getDate();
            this.startTime = spaceBooking.getStartTime();
            this.endTime = spaceBooking.getEndTime();
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving workstation booking, floor or building");
            redirectBack();
        }
    }

    public void validateBooking() {
        if (constructStartDate().isBefore(LocalDateTime.now())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha y hora de inicio es anterior a la actual.", null));
            return;
        }

        if (endTime.isBefore(startTime)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La hora de fin no puede ser anterior a la hora de inicio.", null));
            return;
        }

        this.conflictiveBookings = bookingService.getResourceBookings(spaceBooking.getResource(), constructStartDate(), constructEndDate())
                .stream().filter(b -> !b.getId().equals(this.spaceBooking.getId())).collect(Collectors.toList());

        if (!conflictiveBookings.isEmpty()) {
            PrimeFaces.current().executeScript("PF('widget_dialogConflictiveBookings').show()");
        } else {
            editBooking();
        }
    }

    public LocalDateTime constructStartDate() {
        return LocalDateTime.of(date, startTime);
    }

    public LocalDateTime constructEndDate() {
        return LocalDateTime.of(date, endTime);
    }

    public void editBooking() {
        if (!conflictiveBookings.isEmpty()) {
            bookingService.cancelBookings(conflictiveBookings);
        }
        spaceBooking.setStart(constructStartDate());
        spaceBooking.setEnd(constructEndDate());
        editSpaceService.editBooking(spaceBooking);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Reserva editada correctamente", null));
    }

    public List<UserView> findUsers(String query) {
        List<String> usersToExclude = spaceBooking.getAttendeesUserIds();
        usersToExclude.add(spaceBooking.getOrganizer().getId());

        return userService.findUsersAttendees(query, sessionBean.getOrganizationId(), usersToExclude);
    }

    public void onItemSelect() {
        this.addNewAttendee(new UserView(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getOrganizationId()));
        this.user = null;
    }

    private void addNewAttendee(UserView user) {
        this.spaceBooking.getAttendees().add(new AttendeeView("", this.spaceBooking.getId(), user, AttendeeTypesView.REQUIRED));
    }

    public void removeAttendee(AttendeeView attendeeView) {
        this.spaceBooking.getAttendees().remove(attendeeView);
    }


    private void redirectBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

    public void redirectToList() {
        try {
            String formattedDate = spaceBooking.getDate().format(DateTimeFormatter.BASIC_ISO_DATE);
            String relativeUrl = String.format("resources.xhtml?date=%s&buildingId=%s&floorId=%s", formattedDate, building.getId(), getFloor().getId());
            FacesContext.getCurrentInstance().getExternalContext().redirect(relativeUrl);
        } catch (IOException ex) {
            log.error("Error redirecting to resources.xhtml");
        }
    }
}
