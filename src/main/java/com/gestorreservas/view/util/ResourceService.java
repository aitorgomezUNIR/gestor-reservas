package com.gestorreservas.view.util;

import com.gestorreservas.persistence.BuildingEntity;
import com.gestorreservas.persistence.BuildingRepository;
import com.gestorreservas.persistence.FloorEntity;
import com.gestorreservas.persistence.FloorRepository;
import com.gestorreservas.persistence.resource.ResourceEntity;
import com.gestorreservas.persistence.resource.ResourceRepository;
import com.gestorreservas.view.model.BuildingView;
import com.gestorreservas.view.model.CategoryView;
import com.gestorreservas.view.model.FloorView;
import com.gestorreservas.view.model.ResourceViewLight;
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
public class ResourceService {
    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final ResourceRepository resourceRepository;

    public ResourceViewLight getResource(String resourceId) {
        ResourceEntity r = resourceRepository.findById(resourceId).orElseThrow(() -> new IllegalArgumentException("Unable to find resource with id " + resourceId));
        return new ResourceViewLight(r.getId(), r.getName(), r.getFloorId(), CategoryView.valueOf(r.getCategory().name()));
    }

    public BuildingView getBuilding(String buildingId) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Building with id %s not found", buildingId)));
        return new BuildingView(buildingEntity.getId(), buildingEntity.getName(), buildingEntity.getOrganizationId());
    }

    public FloorView getFloor(String floorId) {
        FloorEntity floorEntity = floorRepository.findById(floorId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Floor with id %s not found", floorId)));
        return new FloorView(floorEntity.getId(), floorEntity.getName(), floorEntity.getBuildingId(), floorEntity.getFloorNumber());
    }
}
