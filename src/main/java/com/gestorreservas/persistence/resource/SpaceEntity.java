package com.gestorreservas.persistence.resource;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_space")
public class SpaceEntity extends ResourceEntity {

    @NotNull
    private Integer capacity;

    public SpaceEntity() {
    }

    public SpaceEntity(String name, String floorId, String organizationId, Integer capacity) {
        super(name, floorId, organizationId);
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
