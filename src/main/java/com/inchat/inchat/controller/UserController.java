package com.inchat.inchat.controller;

import com.inchat.inchat.domain.ChatRoomVO;
import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.domain.UserVO;
import com.inchat.inchat.repository.ChatRepository;
import com.inchat.inchat.service.ChatService;
import com.inchat.inchat.service.RoomService;
import com.inchat.inchat.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatHandler chatHandler;
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

    @Transactional
    @PostMapping("/v1/update-user")
    public UserVO updateUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request, HttpServletResponse response) {
        UserVO user = userService.readUser(userRequestDto);
        String oldNick = user.getNickname();
        user = userService.updateUser(userRequestDto);
        chatRepository.updateUserNick(oldNick, user.getNickname());
        request.getSession().setAttribute("log", user);
        return user;
    }

    @PostMapping("/v1/queue-addition")
    public boolean addQueue(@RequestBody UserRequestDto userRequestDto) {
        UserVO user = userService.readUser(userRequestDto);
        for (UserVO userVO : userQueue) {
            if (userVO.getId().equals(user.getId())) {
                userQueue.remove(userVO);
                break;
            }
        }
        if (user1 != null && user.getId().equals(user1.getId())){
            user1 = null;
        }
        else if(user2 != null && user.getId().equals(user2.getId())){
            user2 = null;
        }
        userQueue.add(user);
        System.out.println(user.getNickname() + " added in Queue\nQueue size : " + userQueue.size());
        return true;
    }

    @PostMapping("/v1/check-queue")
    public synchronized boolean checkQueue(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        if (userQueue.peek() != null && userQueue.peek().getId().equals(userRequestDto.getId())) {
            if (user1 == null) {
                user1 = userQueue.poll();
                System.out.println(user1.getId() + " Waiting");
            } else if (user2 == null) {
                user2 = userQueue.poll();
                System.out.println(user2.getId() + " Waiting");
                roomVO = roomService.findRoomByTwoId(user1.getId(), user2.getId());
                if (roomVO == null && user1 != null) {
                    roomVO = roomService.createChatRoomVO(user1.getId(), user2.getId());
                    System.out.println("Room made");
                }
            }
            return true;
        } else
            return false;
    }

    @PostMapping("/v1/delete-queue")
    public boolean deleteQueue(@RequestBody UserRequestDto userRequestDto) {
        UserVO userVO = userService.readUser(userRequestDto);
        if (user1 != null && userVO.getId().equals(user1.getId())) {
            user1 = null;
            System.out.println("user1 check 부분");
        } else if (user2 != null && userVO.getId().equals(user2.getId())) {
            user2 = null;
            System.out.println("user1 check 부분");
        }
        for (UserVO vo : userQueue) {
            if (vo.getId().equals(userVO.getId())) {
                userQueue.remove(vo);
                return true;
            }
        }
        return false;
    }

    //  채팅방 생성
    @PostMapping("/v1/wait-queue")
    public synchronized int waitQueue(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        if (user1 != null && user2 != null && user1.getId().equals(user2.getId()))
            user2 = null;
        if (roomVO != null) {
            System.out.println(roomVO);
            if (user1 != null && user1.getId().equals(userRequestDto.getId())) {
                user1 = null;
            } else if (user2 != null && user2.getId().equals(userRequestDto.getId())) {
                user2 = null;
            }
            request.getSession().setAttribute("room", roomVO);
            request.getSession().setAttribute("roomList", roomService.findRoomsByUserId(userRequestDto.getId()));
            int code = roomVO.getRoom_code();
            System.out.println("User 1: " + user1);
            System.out.println("User 2: " + user2);
            if (user1 == null && user2 == null) {
                roomVO = null;
                System.out.println("Room Empty\nQueue Size : " + userQueue.size());
            }
            return code;
        }
        return 0;
    }

    @GetMapping("/v3/get-user-list")
    public Set<String> session() {
        return chatHandler.getUserSession();
    }
}