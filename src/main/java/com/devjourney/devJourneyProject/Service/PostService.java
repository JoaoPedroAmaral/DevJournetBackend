package com.devjourney.devJourneyProject.Service;

import com.devjourney.devJourneyProject.Model.Posts;
import com.devjourney.devJourneyProject.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    public Posts getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Posts createPost(Posts post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Posts updatePost(Long id, Posts updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            post.setImage_url(updatedPost.getImage_url());
            post.setTags(updatedPost.getTags());
            return postRepository.save(post);
        }).orElse(null);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}