package com.devjourney.devJourneyProject.Repository;

import com.devjourney.devJourneyProject.Model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {
}