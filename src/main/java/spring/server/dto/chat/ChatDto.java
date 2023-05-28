package spring.server.dto.chat;


import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberPath;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.server.dto.member.MemberDto;
import spring.server.entity.Image;
import spring.server.entity.QMember;
import spring.server.entity.chat.ChatImage;
import spring.server.entity.chat.ChatText;

@Data
@NoArgsConstructor
public class ChatDto {

    private Long roomId;

    private Long chatId;

    private MemberDto sender;

    private Image image;

    private String text;

    public ChatDto(ChatText chatText) {
        this.roomId = chatText.getRoom().getId();
        this.chatId = chatText.getId();
        this.text = chatText.getContent();
        this.sender = new MemberDto(chatText.getMember());
    }
    public ChatDto(ChatImage chatImage) {
        this.roomId = chatImage.getRoom().getId();
        this.chatId = chatImage.getId();
        this.image = chatImage.getImage();
        this.sender = new MemberDto(chatImage.getMember());
    }
}
