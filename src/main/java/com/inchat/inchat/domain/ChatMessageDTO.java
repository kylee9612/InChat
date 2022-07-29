package com.inchat.inchat.domain;

import com.inchat.inchat.util.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name="chat")
public class ChatMessageDTO extends Timestamp {
    @Id
    private int chat_code;
    private String room_code;
    private String writer;
    private String message;

    public void update(String str){
        this.message = str;
    }
}
