package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.server.controller.FeedPostRequest;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.entity.Feed;
import spring.server.entity.User;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final JwtUtil jwtUtil;

    @Transactional
    public FeedPostResponse upload(FeedPostRequest request){
        User loginUser = jwtUtil.getLoginUser();

        Feed feed = Feed.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(loginUser)
                .build();

    }
}
