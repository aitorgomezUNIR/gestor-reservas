package com.gestorreservas.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_building")
public class BuildingEntity extends BaseEntity {

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false, length = 255)
    @NotEmpty(message = "* Name can't be empty")
    private String name;

    @NotNull
    @Column(name = "organization_id", nullable = false, length = 36)
    @NotNull
    private String organizationId;

    public BuildingEntity() {
    }

    public BuildingEntity(String name, String organizationId) {
        this.name = name;
        this.organizationId = organizationId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
