package spring.server.controller;

import static spring.server.result.ResultCode.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.feed.FeedPostRequest;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.result.ResultResponse;
import spring.server.service.FeedService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final FeedService feedService;

    @PostMapping("upload")
    public ResponseEntity<FeedPostResponse> upload(@ModelAttribute FeedPostRequest feedPostRequest){
        log.info("upload controller 실행");
        return ResponseEntity.ok().body(feedService.upload(feedPostRequest));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<ResultResponse> deleteFeed(@PathVariable Long feedId){
        feedService.delete(feedId);

        return ResponseEntity.ok(ResultResponse.of(DeleteFeedSuccess));
    }
}
