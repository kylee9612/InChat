package com.inchat.inchat.controller;

import com.inchat.inchat.domain.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO messageDTO){
        messageDTO.update(messageDTO.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room" + messageDTO.getRoom_code(), messageDTO);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO messageDTO){
        template.convertAndSend("/sub/chat/room/"+messageDTO.getRoom_code(), messageDTO);
    }
}
