package com.gestorreservas.session;

import com.gestorreservas.session.MyUserPrincipal;
import com.gestorreservas.view.model.OrganizationView;
import com.gestorreservas.view.model.UserData;
import com.gestorreservas.persistence.OrganizationEntity;
import com.gestorreservas.persistence.OrganizationRepository;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import java.util.Collections;
import java.util.Objects;
import javax.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Slf4j
@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    UserEntity user;

    @Override
    public UserDetails loadUserByUsername(final @Email String email) {
        return this.getUser(email);
    }

    public UserDetails getUser(final @Email String email) {
        this.user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(email);
        }

        OrganizationEntity org = organizationRepository.findById(user.getOrganizationId()).orElseThrow(() -> new IllegalArgumentException("Unable to retrieve org id"));

        UserData userData = new UserData(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getOrganizationId(), user.getPassword());
        return new MyUserPrincipal(userData, new OrganizationView(org.getId(), org.getName()), Collections.emptyList());
    }
}
