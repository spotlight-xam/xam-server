package spring.server.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Image {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    private String storageUrl;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private Feed feed;
}
