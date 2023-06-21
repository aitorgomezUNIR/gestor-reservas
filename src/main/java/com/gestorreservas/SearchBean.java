package com.gestorreservas;

import com.gestorreservas.persistence.BuildingEntity;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
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
public class SearchBean implements Serializable {

    @Getter
    private List<BuildingEntity> buildings;

    @Getter
    @Setter
    private BuildingEntity selectedBuilding;

    @Getter
    @Setter
    private LocalDate date;

    public SearchBean() {
        this.buildings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            buildings.add(new BuildingEntity("b" + i, "org", "timezone"));
        }
    }

    public void search() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("resources.xhtml");
        } catch (IOException e) {
            log.error("Error redirecting to {}", "resources.xhtml");
        }
    }

}
