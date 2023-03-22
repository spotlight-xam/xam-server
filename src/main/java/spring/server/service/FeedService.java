package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.server.dto.feed.FeedPostRequest;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.entity.Feed;
import spring.server.entity.User;
import spring.server.repository.FeedRepository;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

    private final JwtUtil jwtUtil;
    private final FeedRepository feedRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public FeedPostResponse upload(FeedPostRequest request){

//        User loginUser = jwtUtil.getLoginUser();

        //추후에 리팩토링 필요할 듯
        final List<String> images = request.getImageList().stream()
                .map(image -> {
                    try {
                        log.info("upload try");
                        return s3UploadService.upload(image, "image");
                    } catch (IOException e) {
                        log.info("image 가져오기 실패");
                    }
                    return null;
                }).collect(Collectors.toList());

        Feed feed = Feed.builder()
                .title(request.getTitle())
                .content(request.getContent())
//                .writer(loginUser)
                .build();


        feedRepository.save(feed);



        return new FeedPostResponse(feed.getId());

    }

    @Transactional
    public void delete(Long feedId){
        Optional<Feed> feed = feedRepository.findById(feedId);
        Long loginUserId = jwtUtil.getLoginUserId();

//        if(loginUserId.equals(feed.get))

    }

    private Feed getFeed(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException());
    }
}
