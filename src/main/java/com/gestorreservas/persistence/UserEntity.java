package com.gestorreservas.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    private String organizationId;

    @NotNull
    private String password;

    public UserEntity() {
    }

    public UserEntity(String name, String surname, String email, String organizationId, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.organizationId = organizationId;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
