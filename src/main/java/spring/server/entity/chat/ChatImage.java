package spring.server.entity.chat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spring.server.entity.Image;
import spring.server.entity.Member;
import spring.server.entity.Room;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("IMAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ChatImage extends Chat{

    private Image image;

    public ChatImage(Room room, Member member, Image image) {
        super(room, member);
        this.image = image;
    }
}
