package com.gestorreservas.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 *
 * @author aitor
 */
public interface UserRepository extends JpaRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity> {
    UserEntity findByEmail(String email);
}
