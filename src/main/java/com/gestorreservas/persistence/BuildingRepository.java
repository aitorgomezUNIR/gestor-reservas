package com.gestorreservas.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Aitor Gómez Afonso
 */
public interface BuildingRepository extends JpaRepository<BuildingEntity, String> {
    List<BuildingEntity> findAllByOrganizationId(String organizationId);
}
