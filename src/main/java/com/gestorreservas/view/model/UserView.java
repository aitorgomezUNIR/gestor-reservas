package com.gestorreservas.view.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
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

    public String getFullName() {
        String fullName = this.name;
        if (StringUtils.isNotEmpty(this.surname)) {
            fullName = fullName + " " + this.surname;
        }

        return fullName;
    }
}
