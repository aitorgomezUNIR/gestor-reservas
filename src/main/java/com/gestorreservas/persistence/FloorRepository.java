package com.gestorreservas.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Aitor Gómez Afonso
 */
public interface FloorRepository extends JpaRepository<FloorEntity, String> {
    List<FloorEntity> findAllByBuildingId(String buildingId);
}
