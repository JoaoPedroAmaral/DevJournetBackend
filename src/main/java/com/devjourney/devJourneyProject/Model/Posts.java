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

    @Setter
    @Getter
    @Lob
    @Column(name="image_url", columnDefinition="LONGBLOB")
    private byte[] image_url;


    private LocalDateTime created_at;

    @Setter
    @Getter
    @ElementCollection
    private List<String> tags;

    @Setter
    @Getter
    @ManyToOne
    private Users author;

    @Setter
    @Getter
    private String link;

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

}