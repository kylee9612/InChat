package com.inchat.inchat.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnOpen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class ChatHandler extends TextWebSocketHandler {
    private final ArrayList <WebSocketSession> sessionArrayList = new ArrayList<>();
    private final HashMap <String,WebSocketSession> userSession = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
       log.info(session + "클라이언트 접속");
       System.out.println(session + "클라이언트 접속");
       if(!sessionArrayList.contains(session))
            sessionArrayList.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("payload : "+payload);
        for(WebSocketSession s : sessionArrayList){
            s.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        log.info(session + "클라이언트 접속 해제");
        System.out.println(session + "클라이언트 접속 해제");
        sessionArrayList.remove(session);
        session.close();
    }

    @GetMapping("/v3/get-user-list")
    public ArrayList<WebSocketSession> session(){
        return sessionArrayList;
    }
}
