package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.server.dto.feed.FeedPostRequest;
import spring.server.dto.feed.FeedPostResponse;
import spring.server.dto.feed.FeedResponse;
import spring.server.dto.image.ImageResponse;
import spring.server.entity.Feed;
import spring.server.entity.Image;
import spring.server.entity.Member;
import spring.server.repository.feed.FeedCustomRepositoryImpl;
import spring.server.repository.feed.FeedRepository;
import spring.server.repository.image.ImageRepository;
import spring.server.repository.MemberRepository;
import spring.server.repository.image.ImageRepositoryImpl;
import spring.server.result.error.exception.FeedNotExist;
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
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final ImageRepositoryImpl imageRepositoryImpl;
    private final FeedCustomRepositoryImpl feedCustomRepository;

    @Transactional
    public List<FeedResponse> findAll(){
        List<Feed> allFeed = feedRepository.findAll();
        List<FeedResponse> feedResponseList = new ArrayList<>();

        for (Feed feed : allFeed) {
            List<ImageResponse> imageByFeedId = imageRepositoryImpl.findImageByFeedId(feed.getId());

            FeedResponse feedResponse = FeedResponse.builder()
                    .title(feed.getTitle())
                    .writer(feed.getWriter().getUsername())
                    .images(imageByFeedId)
                    .content(feed.getContent())
                    .build();

            feedResponseList.add(feedResponse);
        }

        return feedResponseList;
    }

    @Transactional
    public FeedPostResponse upload(FeedPostRequest request){

        log.info("피드 서비스 upload 실행");
        Member loginMember = jwtUtil.getLoginMember();

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

        log.info("imagesUrl={}", imagesUrl);

        Feed feed = Feed.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(loginMember)
                .images(imageList)
                .build();

        feedRepository.save(feed);

        for(String url : imagesUrl){
            imageList.add(new Image(url, feed));
        }

        for (Image image : imageList) {

            imageRepository.save(image);
        }

        log.info("imageList={}", imageList);

        return new FeedPostResponse(feed.getId());

    }

    @Transactional
    public void delete(Long feedId){
        Optional<Feed> feed = feedRepository.findById(feedId);
//        jwtUtil.getUserName()

//        if(loginUserId.equals(feed.get))

    }

    private Feed getFeed(Long feedId){
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException());
    }

    public FeedResponse getPost(Long feedId) {
        Member loginMember = jwtUtil.getLoginMember();
        log.info("getPost service 실행");
        final Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotExist::new);
        log.info("=============================");

        List<ImageResponse> imageByFeedId = imageRepositoryImpl.findImageByFeedId(feedId);

        return FeedResponse.builder()
                .writer(loginMember.getUsername())
                .title(feed.getTitle())
                .images(imageByFeedId)
                .content(feed.getContent())
                .build();

    }
}
