package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.server.dto.feed.FeedPostRequest;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.dto.feed.FeedResponse;
import spring.server.entity.Feed;
import spring.server.entity.Image;
import spring.server.entity.User;
import spring.server.repository.FeedRepository;
import spring.server.repository.ImageRepository;
import spring.server.repository.UserRepository;
import spring.server.result.error.exception.FeedNotExist;
import spring.server.result.error.exception.UserNotFoundException;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
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
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public FeedPostResponse upload(FeedPostRequest request){

//        final String name = authentication.getName();
//        final User writer = userRepository.findByUsername(name)
//                .orElseThrow(RuntimeException::new);

//        final Long loginUserId = jwtUtil.getLoginUserId();
//
//        final User writer = userRepository.findById(loginUserId)
//                .orElseThrow(UserNotFoundException::new);


        //추후에 리팩토링 필요할 듯
        final List<String> imagesUrl = request.getImageList().stream()
                .map(image -> {
                    try {
                        log.info("upload try");
                        log.info("image= {}", image);
                        return s3UploadService.upload(image, "image");
                    } catch (IOException e) {
                        log.info("image 가져오기 실패");
                    }
                    return null;
                }).collect(Collectors.toList());

        List<Image> imageList = new ArrayList<>();

        for(String url : imagesUrl){
            imageList.add(new Image(url));
        }


        Feed feed = Feed.builder()
                .title(request.getTitle())
                .content(request.getContent())
//                .writer(writer)
                .images(imageList)
                .build();

        for (Image image : imageList) {
            image.setFeed(feed);
            imageRepository.save(image);
        }


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

    public FeedResponse getPost(Long feedId) {
//        final User loginUser = jwtUtil.getLoginUser();
        log.info("getPost service 실행");
        final Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotExist::new);
        log.info("=============================");

        final FeedResponse feedResponse = FeedResponse.builder()
                .title(feed.getTitle())
//                .writer(feed.getWriter().getUsername())
                .images(feed.getImages())
                .content(feed.getContent())
                .build();


        return feedResponse;
    }
}
