package com.walrus.assignment.repositories;

import com.walrus.assignment.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserNameOrEmailId(String userName, String emailId);
}