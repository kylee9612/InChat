package com.inchat.inchat.controller;

import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.domain.UserVO;
import com.inchat.inchat.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Queue;

@RestController
@Log4j2
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    private Queue<UserVO> userQueue = new LinkedList<>();
    @PostMapping("/v1/get-user")
    public UserVO getUser(@RequestBody UserRequestDto userRequestDto){
        UserVO user= userService.readUser(userRequestDto);
        if(user == null){
            System.out.println("error");
        }
        return user;
    }

    @PostMapping("/v1/add-user")
    public UserVO addUser(@RequestBody UserRequestDto userRequestDto){
        UserVO check = userService.readUser(userRequestDto);
        if(check == null)
            userService.createUser(userRequestDto);
        else
            System.err.println("Duplicated ID");
        return check;
    }

    @PostMapping("/v1/login-user")
    public UserVO loginUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        UserVO check = userService.readUser(userRequestDto);
        if(session.getAttribute("log")==null && check != null){
            session.setAttribute("log",check);
            return check;
        }
        return null;
    }

    @PostMapping("/v1/logout-user")
    public boolean logoutUser(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("log")!=null){
            session.setAttribute("log",null);
            return true;
        }
        else return false;
    }

    @PostMapping("/v1/delete-user")
    public UserVO deleteUser(@RequestBody UserRequestDto userRequestDto,HttpServletRequest request,HttpServletResponse response){
        userService.deleteUser(userRequestDto);
        request.getSession().setAttribute("log",null);
        return null;
    }

    @PostMapping("/v1/update-user")
    public UserVO updateUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request, HttpServletResponse response){
        UserVO user = userService.updateUser(userRequestDto);
        request.getSession().setAttribute("log",user);
        return user;
    }

    @PostMapping("/v1/queue-addition")
    public boolean addQueue(@RequestBody UserRequestDto userRequestDto){
        UserVO user = userService.readUser(userRequestDto);
        userQueue.add(user);
        return true;
    }

    @PostMapping("/v1/check-queue")
    public boolean checkQueue(@RequestBody UserRequestDto userRequestDto){
        UserVO user = userService.readUser(userRequestDto);
        if(userQueue.peek() != null && userQueue.peek().getId().equals(user.getId())){
            userQueue.poll();
            return true;
        }
        return false;
    }
}