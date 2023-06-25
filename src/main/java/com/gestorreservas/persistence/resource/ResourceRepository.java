package com.gestorreservas.persistence.resource;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aitor
 */
public interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
    List<ResourceEntity> findAllByFloorId(String floorId);
}
