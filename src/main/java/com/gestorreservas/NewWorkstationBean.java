package com.gestorreservas;

import com.gestorreservas.model.BookingView;
import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.FloorView;
import com.gestorreservas.model.NewWorkstationBookingView;
import com.gestorreservas.model.ResourceView;
import com.gestorreservas.model.UserView;
import com.gestorreservas.requestparam.RequestParam;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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

    private final NewWorkstationService newWorkstationService;

    @Getter
    private BuildingView building;

    @Getter
    @Setter
    private NewWorkstationBookingView workstationBooking;

    @Getter
    private List<BookingView> conflictiveBookings;



    public NewWorkstationBean(NewWorkstationService newWorkstationService, @RequestParam String start, @RequestParam String end,
            @RequestParam String resourceId) {
        this.newWorkstationService = newWorkstationService;
        this.processParams(start, end, resourceId);
    }

    private void processParams(String start, String end, String resourceId) {
        try {
            long startMs = Long.parseLong(start);
            long endMs = Long.parseLong(end);

            LocalDateTime startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(startMs), ZoneId.of("UTC"));
            LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(endMs), ZoneId.of("UTC"));

            ResourceView resourceView = newWorkstationService.getResource(resourceId);
            FloorView floorView = newWorkstationService.getFloor(resourceView.getFloorId());
            this.building = newWorkstationService.getBuilding(floorView.getBuildingId());
            this.workstationBooking = new NewWorkstationBookingView(resourceView, startDate.toLocalDate(), startDate.toLocalTime(), endDate.toLocalTime(), floorView);
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving resource, building or floor or parsing dates");
            redirectBack();
        }

    }

    public List<UserView> findUsers(String query) {
        return newWorkstationService.findUsers(query, "25f71ffc-93f5-4a37-be19-e27044190559");
    }

    public void validateNewBooking() {
        if (workstationBooking.constructStartDate().isBefore(LocalDateTime.now())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha y hora de inicio es anterior a la actual.", null));
            return;
        }

        this.conflictiveBookings = newWorkstationService.getResourceBookings(workstationBooking.getResource().getId(), workstationBooking.constructStartDate(), workstationBooking.constructEndDate());

        if (conflictiveBookings.isEmpty()) {
            PrimeFaces.current().executeScript("PF('widget_dialogConflictiveBookings').show()");
        } else {
            createBooking();
        }
    }

    public void createBooking() {
        this.newWorkstationService.createWorkstationBooking(workstationBooking);
    }

    private void redirectBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

}
