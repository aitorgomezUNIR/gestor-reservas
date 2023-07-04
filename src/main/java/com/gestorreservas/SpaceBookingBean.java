package com.gestorreservas;

import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.SpaceBookingView;
import com.gestorreservas.view.requestparam.RequestParam;
import com.gestorreservas.view.util.BookingService;
import com.gestorreservas.view.util.ResourceService;
import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
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
public class SpaceBookingBean implements Serializable {

    private final SpaceBookingService spaceBookingService;
    private final ResourceService resourceService;
    private final BookingService bookingService;

    @Getter
    private SpaceBookingView spaceBooking;

    @Getter
    private BuildingView building;

    @Getter
    private FloorView floor;

    public SpaceBookingBean(SpaceBookingService spaceBookingService, ResourceService resourceService, BookingService bookingService, @RequestParam String bookingId) {
        this.spaceBookingService = spaceBookingService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
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

        } catch (IllegalArgumentException e) {
            log.error("Error retrieving space booking, floor or building");
            redirectBack();
        }
    }

    public void checkIn() {
        bookingService.checkIn(spaceBooking);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Check in realizado correctamente", null));
    }

    public void checkOut() {
        bookingService.checkOut(spaceBooking);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Check out realizado correctamente", null));
    }

    public void cancel() {
        bookingService.cancelBooking(spaceBooking);
        redirectBack();
    }

    public void redirectToList() {
        String formattedDate = spaceBooking.getDate().format(DateTimeFormatter.BASIC_ISO_DATE);
        String relativeUrl = String.format("resources.xhtml?date=%s&buildingId=%s&floorId=%s", formattedDate, building.getId(), this.floor.getId());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(relativeUrl);
        } catch (IOException ex) {
            log.error("Error redirecting to {}", relativeUrl);
        }
    }

    private void redirectBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

    public void onEditBooking() {
        String url = String.format("edit_space.xhtml?bookingId=%s", spaceBooking.getId());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            log.error("Error redirecting to {}", url);
        }
    }

}
