package com.inchat.inchat.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

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
    public ModelAndView chatPage(){
        return new ModelAndView("/chatPage");
    }

    @RequestMapping("/roomList")
    public ModelAndView chatList(){
        return new ModelAndView("/roomList");
    }
}