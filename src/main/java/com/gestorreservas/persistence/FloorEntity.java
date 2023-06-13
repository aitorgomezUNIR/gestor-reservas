package com.gestorreservas.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Aitor Gómez Afonso
 */
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "c_floor")
public class FloorEntity extends BaseEntity {
    @Column(nullable = false, length = 255)
    @NotEmpty(message = "* Name can't be empty")
    @NonNull
    private String name;

    @NotNull
    @NonNull
    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(length = 4)
    private String postercode;

    @NotNull
    @NonNull
    @Column(name = "building_id", nullable = false, length = 36)
    private String buildingId;

    public String getName() {
        return name;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public String getPostercode() {
        return postercode;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
