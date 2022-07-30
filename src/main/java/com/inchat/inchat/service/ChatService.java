package com.inchat.inchat.service;

import com.inchat.inchat.domain.ChatMessageDTO;
import com.inchat.inchat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRepository repository;

    public List<ChatMessageDTO> findChatByRoomCode(int code){
        List<ChatMessageDTO> result =  repository.findAll();
        for(int i = 0 ; i < result.size(); i++){
            if(result.get(i).getRoom_code() != code){
                result.remove(i);
                i--;
            }
        }
        return result;
    }
}
