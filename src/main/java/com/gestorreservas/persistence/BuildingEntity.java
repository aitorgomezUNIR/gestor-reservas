package com.gestorreservas.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "c_building",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name", "organization_id"})})
public class BuildingEntity extends BaseEntity {
    @Size(max = 255)
    @NotNull
    @NonNull
    @Column(name = "name", nullable = false, length = 255)
    @NotEmpty(message = "* Name can't be empty")
    private String name;

    @Column(nullable = true, length = 6)
    private String postercode;

    @NotNull
    @Column(name = "organization_id", nullable = false, length = 36)
    @NotNull
    @NonNull
    private String organizationId;

    @Column(name = "timezone", length = 255)
    @NotNull
    @NonNull
    private String timezone;

    public String getPostercode() {
        return postercode;
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
