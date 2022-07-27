package com.inchat.inchat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Table(name="room")
public class ChatRoomVO{
    @Id
    private long room_code;
    private String user1_id;
    private String user2_id;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomVO create_room(String user1_id, String user2_id){
        ChatRoomVO room = new ChatRoomVO();

        room.room_code = Math.round(Math.random());
        room.user1_id = user1_id;
        room.user2_id = user2_id;
        return room;
    }
}
