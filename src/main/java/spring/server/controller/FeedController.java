package spring.server.controller;

import static spring.server.result.ResultCode.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.feed.FeedPostRequest;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.dto.feed.FeedResponse;
import spring.server.result.ResultResponse;
import spring.server.service.FeedService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final FeedService feedService;

    //전체 피드 조회
    @GetMapping("/all")
    public ResponseEntity<List<FeedResponse>> findAll(){
        return ResponseEntity.ok().body(feedService.findAll());
    }

    //피드 업로드
    @PostMapping("/upload")
    public ResponseEntity<FeedPostResponse> upload(@ModelAttribute FeedPostRequest feedPostRequest){
        log.info("upload controller 실행");
        return ResponseEntity.ok().body(feedService.upload(feedPostRequest));
    }

    //피드 삭제
    @DeleteMapping("/{feedId}")
    public ResponseEntity<ResultResponse> deleteFeed(@PathVariable Long feedId){
        feedService.delete(feedId);
        return ResponseEntity.ok(ResultResponse.of(DeleteFeedSuccess));
    }

    //피드 단건 조회
    @GetMapping("/{feedId}")
    public ResponseEntity<FeedResponse> getPost(@PathVariable Long feedId){
        log.info("getPost 실행");
        return ResponseEntity.ok(feedService.getPost(feedId));
    }
}
