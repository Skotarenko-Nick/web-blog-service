package com.webapp.controller;

import com.webapp.exception.UserException;
import com.webapp.model.Post;
import com.webapp.model.User;
import com.webapp.service.PostService;
import com.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public BlogController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(value = "/blog/{username}")
    @ResponseBody
    public List<Post> blogForUsername(@PathVariable String username,
                                      @RequestParam(defaultValue = "0") int page) throws Exception {

        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Post> posts = postService.findByUserOrderedByDatePageable(user, page);
            return posts.getContent();

        } else {
            throw new UserException("user doesn't exist");
        }
    }
}
