package com.walrus.assignment.services;

import com.walrus.assignment.models.Follower;
import com.walrus.assignment.models.Login;
import com.walrus.assignment.models.Post;
import com.walrus.assignment.models.User;
import com.walrus.assignment.repositories.FollowerRepository;
import com.walrus.assignment.repositories.PostRepository;
import com.walrus.assignment.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowerRepository followerRepository;

    public User registerUser(User user){
        user.setPassword(hash(user.getPassword()));
        return userRepository.save(user);
    }

    public User isValidUser(Login login){
        Optional<User> user = userRepository.findByUserNameOrEmailId(login.getUserName(), login.getUserName());
        if(user.isPresent() && verifyHash(login.getPassword(), user.get().getPassword())){
            return user.get();
        }
        return null;
    }

    public Optional<User> getUserDetails(String userNameOrEmailId){
        return userRepository.findByUserNameOrEmailId(userNameOrEmailId, userNameOrEmailId);
    }

    public Post createPost(Post post){
        if(userRepository.findById(post.getUserId()).isPresent() && post.getPost().length()<=140){
            return postRepository.save(post);
        }
        return null;
    }

    public Follower addFollower(Long userId, Long followerUserId){
        if(userRepository.findById(userId).isPresent() && userRepository.findById(followerUserId).isPresent()){
            Follower follower = new Follower();
            follower.setUserId(userId);
            follower.setFollowerUserId(followerUserId);

            return followerRepository.save(follower);
        }
        return null;
    }

    public Post addLike(Long likedByUser, Long postId){
        if(userRepository.findById(likedByUser).isPresent() ){
            Optional<Post> postObject = postRepository.findById(postId);
            if(postObject.isPresent()){
                Post post = postObject.get();
                post.setLikeCount(post.getLikeCount() + 1);

                return postRepository.save(post);
            }
        }
        return null;
    }

    public List<Post> getUserFeed(Long userId){
        if(userRepository.findById(userId).isPresent() ){
            List<Follower> followers = followerRepository.findByFollowerUserId(userId);
            List<Long> userIds = new ArrayList<>();

            for (Follower follower : followers) {
                userIds.add(follower.getUserId());
            }

            return postRepository.findByUserIdIn(userIds);
        }
        return new ArrayList<>();
    }

    public List<Post> getUserFeedHistory(Long userId){
        if(userRepository.findById(userId).isPresent() ){
            return postRepository.findByUserId(userId);
        }
        return new ArrayList<>();
    }

    public String hash(String password) {
        int logRounds = 10;
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
