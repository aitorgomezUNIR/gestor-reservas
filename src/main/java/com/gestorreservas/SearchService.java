package com.gestorreservas;

import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.FloorView;
import com.gestorreservas.persistence.BuildingRepository;
import com.gestorreservas.persistence.FloorRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;

    public List<BuildingView> getOrgBuildings(String orgId) {
        return buildingRepository.findAllByOrganizationId(orgId)
                .stream()
                .map(b -> new BuildingView(b.getId(), b.getName(), b.getOrganizationId()))
                .collect(Collectors.toList());
    }

    public List<FloorView> getBuildingFloors(String buildingId) {
        // TO-DO: Ordenar las plantas!
        return floorRepository.findAllByBuildingId(buildingId)
                .stream()
                .map(f -> new FloorView(f.getId(), f.getName(), f.getBuildingId(), f.getFloorNumber()))
                .collect(Collectors.toList());
    }

}
