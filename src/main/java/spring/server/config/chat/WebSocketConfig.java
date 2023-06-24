package spring.server.config.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@RequiredArgsConstructor
@Configuration
@Slf4j
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        log.info("web socket");
        registry.addHandler(chatHandler, "/ws/chat").setAllowedOrigins("*");


    }


}


//endpoint를 /stomp로 하고, allowedOrigins를 "*"로 하면 페이지에서
//Get /info 404 Error가 발생한다. 그래서 아래와 같이 2개의 계층으로 분리하고
//origins를 개발 도메인으로 변경하니 잘 동작하였다.
//이유는 왜 그런지 아직 찾지 못함
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//
//        log.info("registerStompEndpoints 실행");
//        registry.addEndpoint("/stomp/chat")
//                .setAllowedOrigins("*");
//    }

/*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/

/**
 * setApplicationDestinationPrefixes : Client에서 SEND 요청을 처리
 * Spring docs에서는 /topic, /queue로 나오나 편의상 /pub, /sub로 변경
 * enableSimpleBroker
 * 해당 경로로 SimpleBroker를 등록. SimpleBroker는 해당하는 경로를 SUBSCRIBE하는 Client에게 메세지를 전달하는 간단한 작업을 수행
 */
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//
//        registry.setApplicationDestinationPrefixes("/pub");
//        registry.enableSimpleBroker("/sub");
//    }