package com.devjourney.devJourneyProject.Model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Posts {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String content;
    private String image_url; // URL da imagem (armazenada no AWS S3 ou similar)
    private LocalDateTime created_at;

    @Setter
    @Getter
    @ElementCollection
    private List<String> tags;

    @Setter
    @Getter
    @ManyToOne
    private Users author;

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

}