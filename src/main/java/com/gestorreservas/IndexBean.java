package com.gestorreservas;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author Aitor Gómez Afonso
 */
@RequestScope
@Component
public class IndexBean {
    public void redirect() {
        FacesContext fc = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();

        navigationHandler.handleNavigation(fc, null, "search.xhtml" + "?faces-redirect=true");
    }
}
