package com.gestorreservas;

import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.FloorView;
import com.gestorreservas.model.WorkstationBookingView;
import com.gestorreservas.requestparam.RequestParam;
import com.gestorreservas.session.SessionBean;
import java.io.IOException;
import java.io.Serializable;
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

    private final SessionBean sessionBean;
    private final WorkstationBookingService workstationBookingService;

    @Getter
    private WorkstationBookingView workstationBooking;

    @Getter
    private BuildingView building;

    @Getter
    private FloorView floor;

    public WorkstationBookingBean(SessionBean sessionBean, WorkstationBookingService workstationBookingService, @RequestParam String bookingId) {
        this.sessionBean = sessionBean;
        this.workstationBookingService = workstationBookingService;
        processParams(bookingId);
    }

    private void processParams(String bookingId) {
        if (StringUtils.isBlank(bookingId)) {
            redirectBack();
        }

        try {
            this.workstationBooking = workstationBookingService.getWorkstationBooking(bookingId);
            this.floor = workstationBookingService.getFloor(this.workstationBooking.getResource().getFloorId());
            this.building = workstationBookingService.getBuilding(this.floor.getBuildingId());

        } catch (IllegalArgumentException e) {
            log.error("Error retrieving workstation booking, floor or building");
            redirectBack();
        }
    }

    private void redirectBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

}
