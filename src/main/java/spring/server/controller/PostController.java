package spring.server.controller;

import static spring.server.result.ResultCode.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.result.ResultCode;
import spring.server.result.ResultResponse;
import spring.server.service.PostService;

@RestController("post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<FeedPostResponse> upload(@ModelAttribute FeedPostRequest feedPostRequest){
        return ResponseEntity.ok().body(postService.upload(feedPostRequest));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<ResultResponse> deleteFeed(@PathVariable Long feedId){
        postService.delete(feedId);

        return ResponseEntity.ok(ResultResponse.of(DeleteFeedSuccess));
    }
}
