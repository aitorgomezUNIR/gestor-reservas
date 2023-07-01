package com.gestorreservas.view.model;

import lombok.Data;
import lombok.NonNull;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Data
public class AttendeeView {

    @NonNull
    private String id;

    @NonNull
    private String bookingId;

    @NonNull
    private UserView user;

    @NonNull
    private AttendeeTypesView type;

    private boolean optional;

    public void onChangedOptional() {
        this.type = optional ? AttendeeTypesView.OPTIONAL : AttendeeTypesView.REQUIRED;
    }

}
