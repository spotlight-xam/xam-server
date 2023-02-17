package spring.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Member;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Feed {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;



}
