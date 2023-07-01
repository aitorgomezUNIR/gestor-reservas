package com.gestorreservas;

import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.WorkstationBookingView;
import com.gestorreservas.view.requestparam.RequestParam;
import com.gestorreservas.view.util.BookingService;
import com.gestorreservas.view.util.ResourceService;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Component
@ViewScoped
@Slf4j
public class WorkstationBookingBean implements Serializable {

    private final WorkstationBookingService workstationBookingService;
    private final ResourceService resourceService;
    private final BookingService bookingService;

    @Getter
    private WorkstationBookingView workstationBooking;

    @Getter
    private BuildingView building;

    @Getter
    private FloorView floor;

    public WorkstationBookingBean(WorkstationBookingService workstationBookingService, ResourceService resourceService, BookingService bookingService,
            @RequestParam String workstationBookingId) {
        this.workstationBookingService = workstationBookingService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        this.processParams(workstationBookingId);
    }

    private void processParams(String bookingId) {
        if (StringUtils.isBlank(bookingId)) {
            redirectBack();
        }

        try {
            this.workstationBooking = workstationBookingService.getWorkstationBooking(bookingId);
            this.floor = resourceService.getFloor(this.workstationBooking.getResource().getFloorId());
            this.building = resourceService.getBuilding(this.floor.getBuildingId());
            if (true) {
                int math = 1 + 1;
            }
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving workstation booking, floor or building");
            redirectBack();
        }
    }

    public void checkIn() {
        bookingService.checkIn(workstationBooking);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Check in realizado correctamente", null));
    }

    public void checkOut() {
        bookingService.checkOut(workstationBooking);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Check out realizado correctamente", null));
    }

    public void cancel() {
        bookingService.cancelBooking(workstationBooking);
        redirectBack();
    }

    private void redirectBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

}
