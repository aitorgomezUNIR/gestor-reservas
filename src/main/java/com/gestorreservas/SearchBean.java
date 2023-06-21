package com.gestorreservas;

import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.FloorView;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Component
@ViewScoped
@Slf4j
@RequiredArgsConstructor
public class SearchBean implements Serializable {

    private static final String organizationId = "25f71ffc-93f5-4a37-be19-e27044190559";

    private final SearchService searchService;

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
    @Setter
    private LocalDate date;

    @PostConstruct
    public void init() {
        buildings = searchService.getOrgBuildings(organizationId);
    }

    public void search() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("resources.xhtml");
        } catch (IOException e) {
            log.error("Error redirecting to {}", "resources.xhtml");
        }
    }

    public LocalDate getMinDate() {
        return LocalDate.now();
    }

    public void onSelectedBuilding() {
        this.floors = searchService.getBuildingFloors(selectedBuilding.getId());
        this.selectedFloor = null;
    }

    public void onSelectedFloor() {
        if (Objects.isNull(date)) {
            this.date = LocalDate.now();
        }
    }

}
