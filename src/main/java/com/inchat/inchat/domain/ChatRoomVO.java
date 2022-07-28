package com.inchat.inchat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;

@Entity
@NoArgsConstructor
@Getter
@Table(name="room")
public class ChatRoomVO{
    @Id
    private int room_code;
    private String user1_id;
    private String user2_id;
    private static Random random = new Random();
//    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomVO create_room(String user1_id, String user2_id){
        ChatRoomVO room = new ChatRoomVO();
        room.room_code = random.nextInt(1000000);
        room.user1_id = user1_id;
        room.user2_id = user2_id;
        return room;
    }
}
