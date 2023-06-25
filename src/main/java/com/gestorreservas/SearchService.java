package com.gestorreservas;

import com.gestorreservas.model.BuildingView;
import com.gestorreservas.model.FloorView;
import com.gestorreservas.persistence.BuildingRepository;
import com.gestorreservas.persistence.FloorRepository;
import com.gestorreservas.persistence.resource.ResourceRepository;
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
    private final ResourceRepository resourceRepository;

    public List<BuildingView> getOrgBuildings(String orgId) {
        return buildingRepository.findAllByOrganizationId(orgId)
                .stream()
                .map(b -> new BuildingView(b.getId(), b.getName(), b.getOrganizationId()))
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
    }

    public List<FloorView> getBuildingFloors(String buildingId) {
        // TO-DO: Ordenar las plantas!
        return floorRepository.findAllByBuildingId(buildingId)
                .stream()
                .map(f -> new FloorView(f.getId(), f.getName(), f.getBuildingId(), f.getFloorNumber()))
                .sorted((o1, o2) -> o1.getFloorNumber().compareTo(o2.getFloorNumber()))
                .collect(Collectors.toList());
    }

    /*@Transactional
    public void populateDB() {
        String orgId = "25f71ffc-93f5-4a37-be19-e27044190559";
        List<BuildingEntity> buildings = buildingRepository.findAllByOrganizationId(orgId);
        List<ResourceEntity> resources = new ArrayList<>();
        Random random = new Random();
        for (BuildingEntity b : buildings) {
            List<FloorEntity> floors = floorRepository.findAllByBuildingId(b.getId());
            for (FloorEntity f : floors) {

                int range = (30 - 20) + 1;
                int resourcesNumber = (int) ((Math.random() * range) + 20);

                for (int i = 1; i <= resourcesNumber; i++) {
                    boolean space = random.nextBoolean();
                    ResourceEntity r;
                    if (space) {
                        r = new SpaceEntity("SALA " + i, f.getId(), orgId, 5);
                    } else {
                        r = new WorkstationEntity("PUESTO " + i, f.getId(), orgId);
                    }
                    resources.add(r);
                }
            }
        }
        resourceRepository.saveAll(resources);
    }*/

}
