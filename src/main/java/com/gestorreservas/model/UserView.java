package com.gestorreservas.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class UserView {

    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String email;

    @NonNull
    private String organizationId;
}
