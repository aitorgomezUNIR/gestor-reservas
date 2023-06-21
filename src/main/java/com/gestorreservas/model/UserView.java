package com.gestorreservas.model;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class UserView {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String email;

    @NotNull
    private String organizationId;
}
