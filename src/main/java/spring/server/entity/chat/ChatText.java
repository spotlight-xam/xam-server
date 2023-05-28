package spring.server.entity.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.server.entity.Member;
import spring.server.entity.Room;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TEXT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatText extends Chat{

    private String content;

    public ChatText(Room room, Member member, String content) {
        super(room, member);
        this.content = content;
    }

}
