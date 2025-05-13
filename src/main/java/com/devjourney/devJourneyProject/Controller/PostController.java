package com.devjourney.devJourneyProject.Controller;

import com.devjourney.devJourneyProject.Model.Posts;
import com.devjourney.devJourneyProject.Model.Users;
import com.devjourney.devJourneyProject.Repository.UserRepository;
import com.devjourney.devJourneyProject.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

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

    @PostMapping("/upload")
    public ResponseEntity<Posts> createPostWithImage(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("authorId") Long authorId,
            @RequestParam("link") String link
    ){
        try{
            Users author = userRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Posts post = new Posts();
            post.setTitle(title);
            post.setContent(content);
            if(tags != null && !tags.isEmpty()){
                List<String> tagList = List.of(tags.split(",\\s*"));
                post.setTags(tagList);
            }
            post.setImage_url(imageFile.getBytes());
            post.setAuthor(author);
            post.setCreatedAt(LocalDateTime.now());
            post.setLink(link);

            Posts saved = postService.createPost(post);
            return ResponseEntity.ok(saved);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long id) {
        Posts post = postService.getPostById(id);
        if (post == null || post.getImage_url() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // ou image/png dependendo do tipo
                .body(post.getImage_url());
    }


    @PutMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Posts> updatePostWithImage(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("authorId") Long authorId,
            @RequestParam("link") String link
    ) {
        try {
            Users author = userRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


            Posts post = postService.getPostById(id);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            post.setTitle(title);
            post.setContent(content);
            if (tags != null && !tags.isEmpty()) {
                List<String> tagList = new ArrayList<>(List.of(tags.split(",\\s*")));
                post.setTags(tagList);
            }
            post.setImage_url(imageFile.getBytes());
            post.setAuthor(author);
            post.setLink(link);

            Posts updated = postService.updatePost(id, post);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}