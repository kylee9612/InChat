package com.inchat.inchat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="room")
@ToString
public class ChatRoomVO{
    @Id
    private int room_code;
    private String user1_id;
    private String user2_id;
}
