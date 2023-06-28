package com.gestorreservas;

import com.gestorreservas.session.LoginUserService;
import com.gestorreservas.view.model.UserView;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Named
@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {

    @Autowired
    private LoginUserService userService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return userService.getUser(value);
            } catch (IllegalArgumentException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid user."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((UserView) value).getId());
        } else {
            return null;
        }
    }
}
