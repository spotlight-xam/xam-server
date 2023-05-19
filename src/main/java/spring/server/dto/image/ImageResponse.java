package spring.server.dto.image;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageResponse {
    private String imageUrl;
    private Long id;

    @QueryProjection
    public ImageResponse(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

}
