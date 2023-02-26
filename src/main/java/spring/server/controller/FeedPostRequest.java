package spring.server.controller;


import lombok.Data;
import spring.server.entity.Image;
import spring.server.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedPostRequest {

    private String content;

    private List<Image> imageList = new ArrayList<>();

    private String title;

}
