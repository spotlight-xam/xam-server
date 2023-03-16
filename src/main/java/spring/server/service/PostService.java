//package spring.server.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import spring.server.controller.FeedPostRequest;
//import spring.server.dto.feed.FeedPostResponse;
//import spring.server.entity.Feed;
//import spring.server.entity.User;
//import spring.server.repository.FeedRepository;
//import spring.server.util.JwtUtil;
//
//import javax.transaction.Transactional;
//import java.util.Optional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PostService {
//
//    private final JwtUtil jwtUtil;
//    private final FeedRepository feedRepository;
//
//    @Transactional
//    public FeedPostResponse upload(FeedPostRequest request){
//        User loginUser = jwtUtil.getLoginUser();
//
//        //이미지 업로드 추가 해야됨 (S3사용)
//        Feed feed = Feed.builder()
//                .title(request.getTitle())
//                .content(request.getContent())
//                .writer(loginUser)
//                .build();
//
//        feedRepository.save(feed);
//
//        return new FeedPostResponse(feed.getId());
//
//    }
//
//    @Transactional
//    public void delete(Long feedId){
//        Optional<Feed> feed = feedRepository.findById(feedId);
//        Long loginUserId = jwtUtil.getLoginUserId();
//
////        if(loginUserId.equals(feed.get))
//
//    }
//
//    private Feed getFeed(Long feedId){
//        return feedRepository.findById(feedId)
//                .orElseThrow(() -> new RuntimeException());
//    }
//}
