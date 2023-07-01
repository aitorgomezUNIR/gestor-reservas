package com.gestorreservas;

import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.WorkstationBookingView;
import com.gestorreservas.view.requestparam.RequestParam;
import com.gestorreservas.view.util.BookingService;
import com.gestorreservas.view.util.ResourceService;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
public class EditWorkstationBean implements Serializable {
    private final WorkstationBookingService workstationBookingService;
    private final EditWorkstationService editWorkstationService;
    private final ResourceService resourceService;
    private final BookingService bookingService;

    @Getter
    private WorkstationBookingView workstationBooking;

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

    public EditWorkstationBean(WorkstationBookingService workstationBookingService, ResourceService resourceService,
            BookingService bookingService, EditWorkstationService editWorkstationService,
            @RequestParam String bookingId) {
        this.workstationBookingService = workstationBookingService;
        this.editWorkstationService = editWorkstationService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        this.processParams(bookingId);
    }


    private void processParams(String bookingId) {
        if (StringUtils.isBlank(bookingId)) {
            redirectBack();
        }

        try {
            this.workstationBooking = workstationBookingService.getWorkstationBooking(bookingId);
            this.floor = resourceService.getFloor(this.workstationBooking.getResource().getFloorId());
            this.building = resourceService.getBuilding(this.floor.getBuildingId());
            this.date = workstationBooking.getDate();
            this.startTime = workstationBooking.getStartTime();
            this.endTime = workstationBooking.getEndTime();
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving workstation booking, floor or building");
            redirectBack();
        }
    }

    public void validateBooking() {
        if (Objects.isNull(workstationBooking.getOrganizer())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debes añadir un usuario a la reserva.", null));
            return;
        }

        if (constructStartDate().isBefore(LocalDateTime.now())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha y hora de inicio es anterior a la actual.", null));
            return;
        }

        this.conflictiveBookings = bookingService.getResourceBookings(workstationBooking.getResource(), constructStartDate(), constructEndDate())
                .stream().filter(b -> !b.getId().equals(this.workstationBooking.getId())).collect(Collectors.toList());

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
        workstationBooking.setStart(constructStartDate());
        workstationBooking.setEnd(constructEndDate());
        editWorkstationService.editBooking(workstationBooking);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Reserva editada correctamente", null));
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
            String formattedDate = workstationBooking.getDate().format(DateTimeFormatter.BASIC_ISO_DATE);
            String relativeUrl = String.format("resources.xhtml?date=%s&buildingId=%s&floorId=%s", formattedDate, building.getId(), getFloor().getId());
            FacesContext.getCurrentInstance().getExternalContext().redirect(relativeUrl);
        } catch (IOException ex) {
            log.error("Error redirecting to resources.xhtml");
        }
    }

}
