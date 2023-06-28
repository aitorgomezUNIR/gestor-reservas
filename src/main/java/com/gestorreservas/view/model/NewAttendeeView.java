package com.gestorreservas.view.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Getter
@Setter
public class NewAttendeeView {

    @NonNull
    private UserView user;

    private boolean optional;

    public NewAttendeeView(UserView user) {
        this.user = user;
        this.optional = false;
    }

}
