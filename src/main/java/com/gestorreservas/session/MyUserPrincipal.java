package com.gestorreservas.session;

import com.gestorreservas.model.OrganizationView;
import com.gestorreservas.model.UserData;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Aitor Gómez Afonso
 */
public class MyUserPrincipal implements UserDetails {

    private final UserData user;
    private final OrganizationView organization;
    private final Collection<? extends GrantedAuthority> authorities;

    public MyUserPrincipal(UserData user, OrganizationView organization, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.organization = organization;
        this.authorities = authorities;
    }


    @Override
    public String getUsername() {
        return user.getName();
    }

    public String getUserSurname() {
        return user.getSurname();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public OrganizationView getOrganization() {
        return organization;
    }

    public String getFullName() {
        String fullName = user.getName();
        if (StringUtils.isNotEmpty(user.getSurname())) {
            fullName = fullName + " " + user.getSurname();
        }

        return fullName;
    }
}
