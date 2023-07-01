package com.gestorreservas.view.util;

import com.gestorreservas.view.model.UserView;
import com.gestorreservas.persistence.QUserEntity;
import com.gestorreservas.persistence.UserEntity;
import com.gestorreservas.persistence.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserView> findUsers(String keyword, String organizationId) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(0, 10);

        QUserEntity user = QUserEntity.userEntity;
        keyword = keyword.trim().toLowerCase();

        BooleanExpression booleanExpression = user.organizationId.eq(organizationId)
                .and(user.name.append(" ").concat(user.surname)
                        .containsIgnoreCase(keyword).or(user.email.contains(keyword)));
        return userRepository.findAll(booleanExpression, pageable).map(this::constructUserView).getContent();
    }

    public List<UserView> findUsersAttendees(String keyword, String organizationId, String organizerId) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(0, 10);

        QUserEntity user = QUserEntity.userEntity;
        keyword = keyword.trim().toLowerCase();

        BooleanExpression booleanExpression = user.organizationId.eq(organizationId).and(user.id.notEqualsIgnoreCase(organizerId))
                .and(user.name.append(" ").concat(user.surname)
                        .containsIgnoreCase(keyword).or(user.email.contains(keyword)));
        return userRepository.findAll(booleanExpression, pageable).map(this::constructUserView).getContent();
    }

    private UserView constructUserView(UserEntity entity) {
        return new UserView(entity.getId(), entity.getName(), entity.getSurname(), entity.getEmail(), entity.getOrganizationId());
    }
}
