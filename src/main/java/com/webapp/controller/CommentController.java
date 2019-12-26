package com.webapp.controller;

import com.webapp.exception.BindingResultException;
import com.webapp.exception.PostException;
import com.webapp.exception.UserException;
import com.webapp.model.Comment;
import com.webapp.model.Post;
import com.webapp.model.User;
import com.webapp.service.CommentService;
import com.webapp.service.PostService;
import com.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @PostMapping(value = "/createComment")
    @ResponseBody
    public Post createForNewPost(@Valid Comment comment,
                                BindingResult bindingResult) throws BindingResultException {

        if (bindingResult.hasErrors()) {
            throw new BindingResultException("Something goes wrong, seems data is broken");
        } else {
            commentService.save(comment);
            return comment.getPost();
        }
    }

    @GetMapping(value = "/comments/{postId}")
    @ResponseBody
    public List<Comment> commentsByPostId(@PathVariable Long postId,
                                 Principal principal) throws Exception {

        Optional<Post> post = postService.findForId(postId);

        if (post.isPresent()) {
            Optional<User> user = userService.findByUsername(principal.getName());

            if (user.isPresent()) {
                return (List<Comment>) post.get().getComments();

            } else {
                throw new UserException("User doesn't exist");
            }

        } else {
            throw new PostException("Post doesn't present");
        }
    }

}
