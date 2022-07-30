package com.inchat.inchat.domain;

import com.inchat.inchat.util.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;

@Getter
@Entity
@NoArgsConstructor
@Table(name="chat")
@ToString
public class ChatMessageDTO extends Timestamp {
    @Id
    private int chat_code;
    private int room_code;
    private String writer;
    private String message;

    private static Random random = new Random();

    public void update(String str){
        this.message = str;
    }

    @PostConstruct
    public void init(){
        chat_code = random.nextInt(1000000);
    }
}
