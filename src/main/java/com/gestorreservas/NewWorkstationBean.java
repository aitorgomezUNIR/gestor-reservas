package com.gestorreservas;

import com.gestorreservas.session.MyUserPrincipal;
import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.NewWorkstationBookingView;
import com.gestorreservas.view.model.UserView;
import com.gestorreservas.view.requestparam.RequestParam;
import com.gestorreservas.session.SessionBean;
import com.gestorreservas.view.model.ResourceViewLight;
import com.gestorreservas.view.util.BookingService;
import com.gestorreservas.view.util.ResourceService;
import com.gestorreservas.view.util.UserService;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Component
@ViewScoped
@Slf4j
public class NewWorkstationBean implements Serializable {

    private final SessionBean sessionBean;

    private final NewWorkstationService newWorkstationService;
    private final UserService userService;
    private final BookingService bookingService;
    private final ResourceService resourceService;

    @Getter
    private BuildingView building;

    @Getter
    @Setter
    private NewWorkstationBookingView workstationBooking;

    @Getter
    private List<BookingView> conflictiveBookings;

    public NewWorkstationBean(SessionBean sessionBean, NewWorkstationService newWorkstationService,
            UserService userService, BookingService bookingService, ResourceService resourceService,
            @RequestParam String start, @RequestParam String end,
            @RequestParam String resourceId) {
        this.sessionBean = sessionBean;
        this.newWorkstationService = newWorkstationService;
        this.userService = userService;
        this.bookingService = bookingService;
        this.resourceService = resourceService;
        this.processParams(start, end, resourceId);
    }

    private void processParams(String start, String end, String resourceId) {
        try {
            long startMs = Long.parseLong(start);
            long endMs = Long.parseLong(end);

            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(startMs), ZoneId.of("UTC"));
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(endMs), ZoneId.of("UTC"));

            ResourceViewLight resourceView = newWorkstationService.getWorkstation(resourceId);
            FloorView floorView = resourceService.getFloor(resourceView.getFloorId());
            this.building = resourceService.getBuilding(floorView.getBuildingId());
            MyUserPrincipal loggedUser = sessionBean.getActiveUser();
            UserView creator = new UserView(loggedUser.getId(), loggedUser.getUsername(), loggedUser.getUserSurname(), loggedUser.getFullName(), loggedUser.getOrganization().getId());

            this.workstationBooking = new NewWorkstationBookingView(creator, resourceView, startDate.toLocalDate(), startDate.toLocalTime(), endDate.toLocalTime(), floorView);
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving resource, building or floor or parsing dates");
            redirectToMainView();
        }

    }

    public List<UserView> findUsers(String query) {
        return userService.findUsers(query, sessionBean.getOrganizationId());
    }

    public void validateNewBooking() {
        if (Objects.isNull(workstationBooking.getOrganizer())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debes añadir un organizador a la reserva.", null));
            return;
        }

        if (workstationBooking.getEndTime().isBefore(workstationBooking.getStartTime())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La hora de fin no puede ser anterior a la hora de inicio.", null));
            return;
        }

        if (workstationBooking.constructStartDate().isBefore(LocalDateTime.now())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha y hora de inicio es anterior a la actual.", null));
            return;
        }

        this.conflictiveBookings = bookingService.getResourceBookings(workstationBooking.getResource(), workstationBooking.constructStartDate(), workstationBooking.constructEndDate());

        if (!conflictiveBookings.isEmpty()) {
            PrimeFaces.current().executeScript("PF('widget_dialogConflictiveBookings').show()");
        } else {
            createBooking();
        }
    }

    public void createBooking() {
        if (!conflictiveBookings.isEmpty()) {
            bookingService.cancelBookings(conflictiveBookings);
        }

        String bookingId = this.newWorkstationService.createWorkstationBooking(workstationBooking);
        String url = String.format("workstation_booking.xhtml?bookingId=%s", bookingId);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException ex) {
            log.error("Error redirecting to {}", url);
        }
    }

    public void redirectToList() {
        String formattedDate = workstationBooking.getDate().format(DateTimeFormatter.BASIC_ISO_DATE);
        String relativeUrl = String.format("resources.xhtml?date=%s&buildingId=%s&floorId=%s", formattedDate, building.getId(), workstationBooking.getFloor().getId());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(relativeUrl);
        } catch (IOException ex) {
            log.error("Error redirecting to {}", relativeUrl);
        }
    }

    private void redirectToMainView() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

    public LocalDate getMinDate() {
        return LocalDate.now();
    }

}
