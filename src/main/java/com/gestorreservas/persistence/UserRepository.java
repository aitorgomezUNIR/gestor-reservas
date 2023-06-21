package com.gestorreservas.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author aitor
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
