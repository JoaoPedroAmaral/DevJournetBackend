package com.devjourney.devJourneyProject.Controller;

import com.devjourney.devJourneyProject.Model.Posts;
import com.devjourney.devJourneyProject.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Posts> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Posts getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Posts createPost(@RequestBody Posts post) {
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Posts updatePost(@PathVariable Long id, @RequestBody Posts post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}