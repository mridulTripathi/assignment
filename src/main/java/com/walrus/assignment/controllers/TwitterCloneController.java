package com.walrus.assignment.controllers;

import com.walrus.assignment.models.Follower;
import com.walrus.assignment.models.Login;
import com.walrus.assignment.models.Post;
import com.walrus.assignment.models.User;
import com.walrus.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TwitterCloneController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String test(){
        return "Server up & Running";
    }

    @PostMapping("/user/save")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("hello");
        User response = userService.registerUser(user);

        if(response == null){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody Login login) {
        User response = userService.isValidUser(login);

        if(response == null){
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/user/search")
    @ResponseBody
    public ResponseEntity<?> fetchUser(@RequestParam String userName) {
        Optional<User> response = userService.getUserDetails(userName);

        if(response.isPresent()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/post/create")
    @ResponseBody
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        Post response = userService.createPost(post);

        if(response == null){
            return new ResponseEntity<>(new Post(), HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/user/follow")
    @ResponseBody
    public ResponseEntity<?> followUser(@RequestParam Long userId, @RequestParam Long followedByUserId) {
        Follower response = userService.addFollower(userId, followedByUserId);

        if(response == null){
            return new ResponseEntity<>(new Follower(), HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/post/like")
    @ResponseBody
    public ResponseEntity<?> likePost(@RequestParam Long likedByUser, @RequestParam Long postId) {
        Post response = userService.addLike(likedByUser, postId);

        if(response == null){
            return new ResponseEntity<>(new Follower(), HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/user/feed")
    @ResponseBody
    public ResponseEntity<?> showFeed(@RequestParam Long userId) {
        List<Post> response = userService.getUserFeed(userId);

        if(response.size()>0){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/feed/history")
    @ResponseBody
    public ResponseEntity<?> showFeedHistory(@RequestParam Long userId) {
        List<Post> response = userService.getUserFeedHistory(userId);

        if(response.size()>0){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
