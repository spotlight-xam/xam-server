package spring.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Feed {

    @Id @GeneratedValue
    @Column(name = "feed_id")
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @OneToMany(mappedBy = "feed")
    @JsonIgnore
    private List<Image> images = new ArrayList<>();

    private String content;

    private String comment;

    @Builder
    public Feed(String title, User writer, String content, List<Image> images) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.images = images;
    }
}
