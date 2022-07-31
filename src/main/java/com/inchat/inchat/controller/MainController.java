package com.inchat.inchat.controller;

import com.inchat.inchat.domain.BoardVO;
import com.inchat.inchat.domain.ChatMessageDTO;
import com.inchat.inchat.service.BoardService;
import com.inchat.inchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private BoardService boardService;

    @RequestMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("index"); }

    @RequestMapping("/index")
    public ModelAndView index(){
        return mainPage();
    }
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }
    @RequestMapping("/joinIn")
    public ModelAndView joinIn(){
        return new ModelAndView ("join");
    }
    @RequestMapping("/mypage")
    public ModelAndView myPage() { return new ModelAndView("mypage");}

    @RequestMapping("/chatroom")
    public ModelAndView chatRoom(){
        return new ModelAndView("chatRoom");
    }

    @RequestMapping("/loading")
    public ModelAndView loading(){
        return new ModelAndView("loading");
    }

    @RequestMapping("/chatPage")
    public ModelAndView chatPage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("chatPage");
        int roomCode = Integer.parseInt(request.getParameter("room-code"));
        List<ChatMessageDTO> chatList = chatService.findChatByRoomCode(roomCode);
        mv.addObject("chatList",chatList);
        return mv;
    }

    @RequestMapping("/community")
    public ModelAndView community(){
        ModelAndView mv = new ModelAndView("community");
        List<BoardVO> list = boardService.findAllBoardOrderByCode();
        mv.addObject("boardList",list);
        System.out.println(mv);
        System.out.println(list);
        return mv;
    }

    @RequestMapping("/communityWrite")
    public ModelAndView communityWrite(){
        return new ModelAndView("communityWrite");
    }

    @RequestMapping("/communityView")
    public ModelAndView communityView(){
        return new ModelAndView("communityView");
    }
}