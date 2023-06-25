package com.gestorreservas.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Component
@SessionScope
public class SessionBean {
    private MyUserPrincipal activeUser;

    public SessionBean() {
        this.init();
    }

    private void init() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //authorities = AuthorityUtils.authorityListToSet(auth.getAuthorities());

        activeUser = (MyUserPrincipal) auth.getPrincipal();
    }

    public MyUserPrincipal getActiveUser() {
        return activeUser;
    }

    public String getOrganizationId() {
        return activeUser.getOrganization().getId();
    }

    public String getUrlLogout() {
        return "/user/log/out";
    }

}
