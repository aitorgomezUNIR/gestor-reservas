package com.gestorreservas.persistence.resource;

import com.gestorreservas.persistence.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_resource")
public abstract class ResourceEntity extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String floorId;

    @NotNull
    private String organizationId;

    protected ResourceEntity() {
    }

    protected ResourceEntity(String name, String floorId, String organizationId) {
        this.name = name;
        this.floorId = floorId;
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public String getFloorId() {
        return floorId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
