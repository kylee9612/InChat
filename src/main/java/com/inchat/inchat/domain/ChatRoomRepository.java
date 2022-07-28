package com.inchat.inchat.domain;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Repository
public class ChatRoomRepository {

    private HashMap<Integer, ChatRoomVO> chatRoomVOHashMap;

    @PostConstruct
    private void init(){
        chatRoomVOHashMap = new LinkedHashMap<>();
    }

    public ArrayList<ChatRoomVO> findAllRooms(){
        ArrayList<ChatRoomVO> result = new ArrayList<>(chatRoomVOHashMap.values());
        //  최신 순으로 return
        Collections.reverse(result);

        return result;
    }

    public ChatRoomVO findRoomByCode(int code){
        return chatRoomVOHashMap.get(code);
    }

    public ChatRoomVO createChatRoomVO(String id1, String id2){
        ChatRoomVO room = ChatRoomVO.create_room(id1,id2);
        chatRoomVOHashMap.put(room.getRoom_code(),room);
        return room;
    }
}