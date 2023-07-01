package com.gestorreservas;

import com.gestorreservas.view.requestparam.RequestParam;
import com.gestorreservas.view.model.BookingView;
import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.OccupationLegendView;
import com.gestorreservas.view.model.ResourceView;
import com.gestorreservas.session.SessionBean;
import com.gestorreservas.view.util.BookingService;
import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
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
public class ResourceBean implements Serializable {

    private final SessionBean sessionBean;

    private final ResourceListService resourceService;
    private final BookingService bookingService;

    @Getter
    @Setter
    private LocalDate selectedDate;

    @Getter
    private List<BuildingView> buildings;

    @Getter
    @Setter
    private BuildingView selectedBuilding;

    @Getter
    private List<FloorView> floors;

    @Getter
    @Setter
    private FloorView selectedFloor;

    @Getter
    private List<ResourceView> floorResources;

    @Getter
    private List<ResourceView> filteredResources;

    @Getter
    @Setter
    private ResourceView selectedResource;

    @Getter
    private OccupationLegendView occupationLegendView;

    @Getter
    private ResourceFilters filters;

    public ResourceBean(SessionBean sessionBean, ResourceListService resourceService,
            BookingService bookingService,
            @RequestParam String date, @RequestParam String buildingId, @RequestParam String floorId) {
        this.sessionBean = sessionBean;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
        processParams(date, buildingId, floorId);
        init();
    }

    private void processParams(String date, String buildingId, String floorId) {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(buildingId) || StringUtils.isBlank(floorId)) {
            log.error("Date, buildingId or floorId are empty, redirecting back to main view...");
            redirectBack();
        }

        try {
            this.selectedDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
            this.selectedBuilding = resourceService.getBuilding(buildingId);
            this.selectedFloor = resourceService.getFloor(floorId);
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving building and floor");
            redirectBack();
        } catch (DateTimeParseException e) {
            log.error("Error parsing date found in URL: {}", date);
            redirectBack();
        }

    }

    private void init() {
        buildings = resourceService.getOrgBuildings(sessionBean.getOrganizationId());
        floors = resourceService.getBuildingFloors(selectedBuilding.getId());
        LocalTime startTime = resourceService.roundMinutes(LocalTime.now());
        LocalTime endTime = startTime.withHour(23).withMinute(45);
        this.floorResources = resourceService.getFloorResources(selectedFloor.getId(), constructDate(startTime), constructDate(endTime));
        this.filteredResources = new ArrayList<>(floorResources);
        this.occupationLegendView = resourceService.constructOccupationLegend(filteredResources);
        this.filters = new ResourceFilters(startTime, endTime);
    }

    public void applyFilters() {
        this.filteredResources = filters.applyFilters(floorResources);
        resourceService.updateAvailabilityStatus(filteredResources, selectedFloor.getId(), constructDate(filters.getStartTime()), constructDate(filters.getEndTime()));
        this.occupationLegendView = resourceService.constructOccupationLegend(filteredResources);
    }

    public void resetFilters() {
        this.filters.resetFilters();
        this.filteredResources = new ArrayList<>(floorResources);
        this.occupationLegendView = resourceService.constructOccupationLegend(filteredResources);
    }

    private LocalDateTime constructDate(LocalTime time) {
        return LocalDateTime.of(selectedDate, time);
    }

    public LocalDate getMinDate() {
        return LocalDate.now();
    }

    public String getDateTimeMessage() {
        LocalDateTime start = constructDate(this.filters.getStartTime());
        LocalDateTime end = constructDate(this.filters.getEndTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String message = "Ocupación desde <b>{0}</b> hasta <b>{1}</b>";
        String formattedStart = start.format(formatter).replace(" ", " - ");
        String formattedEnd = end.format(formatter).replace(" ", " - ");
        return MessageFormat.format(message, formattedStart, formattedEnd);
    }

    private void redirectBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("search.xhtml");
        } catch (IOException ex) {
            log.error("Error redirecting to search.xhtml");
        }
    }

    public void checkIn(BookingView bookingView) {
        bookingService.checkIn(bookingView);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Check in realizado correctamente", null));
    }

    public void checkOut(BookingView bookingView) {
        bookingService.checkOut(bookingView);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Check out realizado correctamente", null));
    }

    public void cancel(BookingView bookingView) {
        bookingService.cancelBooking(bookingView);
        this.selectedResource.getBookings().remove(bookingView);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva anulada correctamente", null));
    }

    // LISTENERS
    public void onChangedBuilding() {
        floors = resourceService.getBuildingFloors(selectedBuilding.getId());
        selectedFloor = floors.get(0);
        onChangedFloor();
    }

    public void onChangedFloor() {
        this.floorResources = resourceService.getFloorResources(selectedFloor.getId(), constructDate(this.filters.getStartTime()), constructDate(this.filters.getEndTime()));
        this.filteredResources = new ArrayList<>(floorResources);
        this.occupationLegendView = resourceService.constructOccupationLegend(filteredResources);
    }

    public void onChangedDate() {
        this.resourceService.updateAvailabilityStatus(floorResources, selectedFloor.getId(), constructDate(this.filters.getStartTime()), constructDate(this.filters.getEndTime()));
        this.occupationLegendView = resourceService.constructOccupationLegend(filteredResources);
    }

    public void onRowSelect() {
        List<BookingView> resourceBookings = this.resourceService.getResourceBookings(selectedResource, selectedDate);
        this.selectedResource.setBookings(resourceBookings);
    }

    public void onNewBooking() {
        try {
            long startMs = constructDate(filters.getStartTime()).toInstant(ZoneOffset.UTC).toEpochMilli();
            long endMs = constructDate(filters.getEndTime()).toInstant(ZoneOffset.UTC).toEpochMilli();
            String params = String.format("start=%s&end=%s&resourceId=%s", startMs, endMs, selectedResource.getId());
            String url = String.format("new_%s.xhtml?%s", selectedResource.getCategory().name().toLowerCase(), params);
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            log.error("Error redirecting to new booking view");
        }
    }
}
