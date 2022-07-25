package com.inchat.inchat.configuration;

import com.inchat.inchat.domain.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //.withSockJS() 추가
        //setAllowedOrigins("*")에서 *라는 와일드 카드를 사용하면
        //보안상의 문제로 전체를 허용하는 것보다 직접 하나씩 지정해주어야 한다고 한다.
    //        registry.addHandler(chatHandler,"ws/chat")
//                .setAllowedOrigins("localhost:8084").withSockJS()
//                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js");
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }

}
