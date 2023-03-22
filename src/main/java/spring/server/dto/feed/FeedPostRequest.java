package spring.server.dto.feed;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import spring.server.entity.Image;
import spring.server.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedPostRequest {

    private String content;

    private List<MultipartFile> imageList = new ArrayList<>();

    private String title;

}
