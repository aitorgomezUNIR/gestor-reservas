package com.gestorreservas.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_floor",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"floor_number", "building_id"})})
public class FloorEntity extends BaseEntity {

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "* Name can't be empty")

    private String name;

    @NotNull
    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @NotNull
    @Column(name = "building_id", nullable = false, length = 36)
    private String buildingId;

    public FloorEntity() {
    }

    public FloorEntity(String name, Integer floorNumber, String buildingId) {
        this.name = name;
        this.floorNumber = floorNumber;
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
