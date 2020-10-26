package com.walrus.assignment.repositories;

import com.walrus.assignment.models.Follower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowerRepository extends CrudRepository<Follower, Long> {
    List<Follower> findByFollowerUserId(Long followerUserId);
}
