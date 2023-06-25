package com.gestorreservas.persistence.resource;

import com.gestorreservas.persistence.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_resource")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ResourceEntity extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String floorId;

    @NotNull
    private String organizationId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoryTypes category;

    protected ResourceEntity() {
    }

    public ResourceEntity(String name, String floorId, String organizationId, CategoryTypes category) {
        this.name = name;
        this.floorId = floorId;
        this.organizationId = organizationId;
        this.category = category;
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

    public CategoryTypes getCategory() {
        return category;
    }
    public void setName(String name) {
        this.name = name;
    }

}
