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

    public SpaceEntity(Integer capacity, String name, String floorId, String organizationId, CategoryTypes category) {
        super(name, floorId, organizationId, category);
        this.capacity = capacity;
    }


    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
