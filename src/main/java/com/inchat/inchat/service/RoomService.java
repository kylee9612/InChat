package com.inchat.inchat.service;

import com.inchat.inchat.domain.ChatRoomVO;
import com.inchat.inchat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;

    public List<ChatRoomVO> findAllRooms(){
        List<ChatRoomVO> result = repository.findAll();
        //  최신 순으로 return
        Collections.reverse(result);

        return result;
    }

    public List<ChatRoomVO> findRoomsByUserId(String id){
        List<ChatRoomVO> result = repository.findAll();
        for(int i = 0 ; i < result.size(); i++){
            if(!result.get(i).getUser1_id().equals(id) && !result.get(i).getUser2_id().equals(id)){
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    public ChatRoomVO findRoomByTwoId(String id1, String id2){
        List<ChatRoomVO> result = repository.findAll();
        //  최신 순으로 return
        Collections.reverse(result);
        for(ChatRoomVO room : result){
            if(room.getUser1_id().equals(id1) && room.getUser2_id().equals(id2) ||
            room.getUser1_id().equals(id2) && room.getUser2_id().equals(id1))
                return room;
        }
        return null;
    }

    public ChatRoomVO findRoomByCode(int code){
        if(repository==null){
            System.out.println("으아아아아ㅏ앙ㅇ");
        }
        repository.findAll();
        List<ChatRoomVO> result = repository.findAll();
        for(ChatRoomVO room : result){
            if(room.getRoom_code() == code){
                return room;
            }
        }
        return null;
    }

    public ChatRoomVO createChatRoomVO(String id1, String id2){
        ChatRoomVO room = ChatRoomVO.create_room(id1,id2);
        System.out.println(room.getRoom_code() + " Created");
        repository.save(room);
        return room;
    }

    public void deleteChatRoomVO(int code){
        ChatRoomVO room = findRoomByCode(code);
        if(room!=null)
            repository.deleteById(room.getRoom_code());
    }
}
