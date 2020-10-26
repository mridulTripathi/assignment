package com.walrus.assignment.repositories;

import com.walrus.assignment.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    public List<Post> findByUserIdIn(List<Long> userIdList);
    public List<Post> findByUserId(Long userId);
}
