package spring.server.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    private String storageUrl;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
