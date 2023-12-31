package com.gestorreservas.view.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserData implements Serializable {

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

    @NonNull
    private String password;
}
