package com.inchat.inchat.service;

import com.inchat.inchat.domain.ChatRoomVO;
import com.inchat.inchat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;
    private HashMap<Integer, ChatRoomVO> chatRoomVOHashMap = new LinkedHashMap<>();

    @PostConstruct
    public void init(){
        List<ChatRoomVO> list = repository.findAll();
        for(ChatRoomVO room : list){
            chatRoomVOHashMap.put(room.getRoom_code(),room);
        }
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
        System.out.println(room.getRoom_code());
        repository.save(room);
        chatRoomVOHashMap.put(room.getRoom_code(),room);
        return room;
    }

    public void deleteChatRoomVO(int code){
        ChatRoomVO room = findRoomByCode(code);
        if(room!=null)
            repository.deleteById(room.getRoom_code());
    }
}
