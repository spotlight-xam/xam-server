package spring.server.dto.chat;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;
import spring.server.service.ChatService;

@Data
public class ChatDto {



    private String roomId;// 방 번호
    private String sender;//채팅을 보낸 사람
    private String message;// 메세지
    private String time; // 채팅 발송 시간


}
