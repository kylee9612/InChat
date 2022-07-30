package com.inchat.inchat.controller;

import com.inchat.inchat.domain.ChatMessageDTO;
import com.inchat.inchat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;
    @Autowired
    private ChatRepository repository;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO messageDTO){
        messageDTO.update(messageDTO.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/rooms/" + messageDTO.getRoom_code(), messageDTO);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO messageDTO){
        messageDTO.init();
        System.out.println(messageDTO);
        repository.save(messageDTO);
        template.convertAndSend("/sub/chat/rooms/"+messageDTO.getRoom_code(), messageDTO);
    }
}
