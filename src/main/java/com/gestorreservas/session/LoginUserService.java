package com.gestorreservas.session;

import com.gestorreservas.model.UserView;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Service
@RequiredArgsConstructor
public class LoginUserService {
    private final UserRepository userRepository;

    public UserView getUser(String id) {
        UserEntity entity = userRepository.findById(id).orElseThrow();
        return new UserView(entity.getId(), entity.getName(), entity.getSurname(), entity.getEmail(), entity.getOrganizationId());
    }
}
