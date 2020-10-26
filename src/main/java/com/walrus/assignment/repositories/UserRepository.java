package com.walrus.assignment.repositories;

import com.walrus.assignment.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByIsbn(String isbn);

    List<User> findByTitleContaining(String title);
}