package com.inchat.inchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @RequestMapping("/")
    public String mainPage() {return "/WEB-INF/views/index.jsp"; }

    @RequestMapping("/index")
    public String index(){
        return "/WEB-INF/views/index.jsp";
    }
    @RequestMapping("/login")
    public String login(){
        return "/WEB-INF/views/login.jsp";
    }
    @RequestMapping("/joinIn")
    public String joinIn(){
        return "/WEB-INF/views/join.jsp";
    }

}