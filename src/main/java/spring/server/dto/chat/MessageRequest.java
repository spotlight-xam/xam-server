package spring.server.dto.chat;

import lombok.Data;

@Data
public class MessageRequest {

    private Long roomId;// 방 번호
    private Long senderId;//채팅을 보낸 사람
    private String message;// 메세지
    private String time; // 채팅 발송 시간


}
