package com.gestorreservas.persistence.resource;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_workstation")
public class WorkstationEntity extends ResourceEntity {

    public WorkstationEntity() {
    }

    public WorkstationEntity(String name, String floorId, String organizationId) {
        super(name, floorId, organizationId);
    }


}
