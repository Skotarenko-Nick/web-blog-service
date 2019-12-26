package com.webapp.controller;

import com.webapp.exception.BindingResultException;
import com.webapp.exception.PostException;
import com.webapp.model.Post;
import com.webapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/newPost")
    @ResponseBody
    public Post createNewPost(@Valid Post post,
                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new BindingResultException("Something goes wrong, seems data is broken");
        } else {
            return postService.save(post);
        }
    }

    @PostMapping(value = "/editPost/{id}")
    @ResponseBody
    public Post editPostWithId(@PathVariable Long id,
                                 @Valid Post post,
                                 Principal principal) throws PostException {

        Optional<Post> optionalPost = postService.findForId(id);

        if (optionalPost.isPresent()) {

            if (isPrincipalOwnerOfPost(principal, optionalPost.get())) {
                return postService.save(post);
            } else {
                throw new PostException("This user is not an owner of the post");
            }

        } else {
            throw new PostException("The post doesn't present");
        }
    }

    @GetMapping(value = "/post/{id}")
    @ResponseBody
    public Post getPostWithId(@PathVariable Long id) throws PostException {

        Optional<Post> optionalPost = postService.findForId(id);

        if (optionalPost.isPresent()) {
            return optionalPost.get();
        } else {
            throw new PostException("The post doesn't present");
        }
    }

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> home(@RequestParam(defaultValue = "0") int page) {

        Page<Post> posts = postService.findAllOrderedByDatePageable(page);
        return posts.getContent();
    }

    @GetMapping("/posts/all")
    @ResponseBody
    public List<Post> home() {
        return postService.findAll();
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deletePostWithId(@PathVariable Long id,
                                                   Principal principal) throws PostException {

        Optional<Post> optionalPost = postService.findForId(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (isPrincipalOwnerOfPost(principal, post)) {
                postService.delete(post);
                return ResponseEntity.ok().build();
            } else {
                throw new PostException("This user is not an owner of the post");
            }

        } else {
            throw new PostException("The post doesn't present");
        }
    }

    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }
}
