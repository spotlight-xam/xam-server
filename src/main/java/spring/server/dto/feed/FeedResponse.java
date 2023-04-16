package spring.server.dto.feed;

import lombok.Builder;
import lombok.Data;
import spring.server.entity.Image;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedResponse {

    private String title;

    private String writer;

    private List<Image> images = new ArrayList<>();

    private String content;

    @Builder
    public FeedResponse(String title, String writer, List<Image> images, String content){
        this.title = title;
        this.writer = writer;
        this.images = images;
        this.content = content;
    }
}
