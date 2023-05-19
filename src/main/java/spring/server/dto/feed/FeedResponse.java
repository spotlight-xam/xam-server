package spring.server.dto.feed;

import com.querydsl.core.Tuple;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import spring.server.dto.image.ImageResponse;
import spring.server.dto.image.QImageResponse;
import spring.server.entity.Image;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedResponse {
    private String title;
    private String writer;
    private List<ImageResponse> images;
    private String content;

    @Builder
    public FeedResponse(String title, String writer, List<ImageResponse> images, String content){
        this.title = title;
        this.writer = writer;
        this.images = images;
        this.content = content;
    }
}
