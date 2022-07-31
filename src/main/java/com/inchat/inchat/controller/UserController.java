package com.inchat.inchat.controller;

import com.inchat.inchat.domain.ChatRoomVO;
import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.domain.UserVO;
import com.inchat.inchat.service.ChatService;
import com.inchat.inchat.service.RoomService;
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
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ChatService chatService;
    private final Queue<UserVO> userQueue = new LinkedList<>();

    private UserVO user1;
    private UserVO user2;
    private ChatRoomVO roomVO;

    @PostMapping("/v1/get-user")
    public UserVO getUser(@RequestBody UserRequestDto userRequestDto) {
        UserVO user = userService.readUser(userRequestDto);
        if (user == null) {
            System.out.println("error");
        }
        return user;
    }

    @PostMapping("/v1/add-user")
    public UserVO addUser(@RequestBody UserRequestDto userRequestDto) {
        UserVO check = userService.readUser(userRequestDto);
        if (check == null)
            userService.createUser(userRequestDto);
        else
            System.err.println("Duplicated ID");
        return check;
    }

    @PostMapping("/v1/login-user")
    public UserVO loginUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserVO check = userService.readUser(userRequestDto);
        if (session.getAttribute("log") == null && check != null && userRequestDto.getPw().equals(check.getPw())) {
            session.setAttribute("log", check);
            session.setAttribute("roomList", roomService.findRoomsByUserId(check.getId()));
            return check;
        }
        return null;
    }

    @PostMapping("/v1/logout-user")
    public boolean logoutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("log") != null) {
            session.setAttribute("log", null);
            return true;
        } else return false;
    }

    @PostMapping("/v1/delete-user")
    public UserVO deleteUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request, HttpServletResponse response) {
        userService.deleteUser(userRequestDto);
        request.getSession().setAttribute("log", null);
        return null;
    }

    @PostMapping("/v1/update-user")
    public UserVO updateUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request, HttpServletResponse response) {
        UserVO user = userService.updateUser(userRequestDto);
        request.getSession().setAttribute("log", user);
        return user;
    }

    @PostMapping("/v1/queue-addition")
    public boolean addQueue(@RequestBody UserRequestDto userRequestDto) {
        UserVO user = userService.readUser(userRequestDto);
        userQueue.add(user);
        System.out.println(user.getNickname() + " added in Queue / size : " + userQueue.size());
        return true;
    }

    @PostMapping("/v1/check-queue")
    public boolean checkQueue(@RequestBody UserRequestDto userRequestDto) {
        if (userQueue.peek() != null && userQueue.peek().getId().equals(userRequestDto.getId())) {
            if (user1 == null) {
                user1 = userQueue.poll();
                System.out.println(user1.getId() + " Waiting");
            } else if (user2 == null) {
                user2 = userQueue.poll();
                System.out.println(user2.getId() + " Waiting");
                roomVO = roomService.createChatRoomVO(user1.getId(), user2.getId());
                System.out.println("Room made");
            }
            return true;
        } else
            return false;
    }

    //  채팅방 생성
    @PostMapping("/v1/wait-queue")
    public int waitQueue(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        if (roomVO != null) {
            if(user1 != null && user1.getId().equals(userRequestDto.getId())){
                user1 = null;
            }else if(user2 != null && user2.getId().equals(userRequestDto.getId())){
                user2 = null;
            }
            request.getSession().setAttribute("room", roomVO);
            request.getSession().setAttribute("roomList", roomService.findRoomsByUserId(userRequestDto.getId()));
            int code = roomVO.getRoom_code();
            if(user1 == null && user2 == null)
                roomVO = null;
            return code;
        }
        return 0;
    }
}