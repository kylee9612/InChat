package com.inchat.inchat.controller;

import com.inchat.inchat.domain.ChatRoomVO;
import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.domain.UserVO;
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
    private UserService userService = new UserService();
    @Autowired
    private RoomService roomService = new RoomService();
    private Queue<UserVO> userQueue = new LinkedList<>();

    private UserVO user1;
    private UserVO user2;
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
        if(session.getAttribute("log")==null && check != null && userRequestDto.getPw().equals(check.getPw())){
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
        System.out.println(user.getNickname() + " added in Queue / size : "+userQueue.size());
        return true;
    }

    @PostMapping("/v1/check-queue")
    public boolean checkQueue(@RequestBody UserRequestDto userRequestDto){
        if(userQueue.peek() != null && userQueue.peek().getId().equals(userRequestDto.getId())){
            if(user1 == null) {
                user1 = userQueue.poll();
                System.out.println(user1.getId() + "Waiting");
            }
            else {
                user2 = userQueue.poll();
                System.out.println(user2.getId() + "Waiting");
            }
            return true;
        }else
            return false;
    }

    //  채팅방 생성
    @PostMapping("/v1/wait-queue")
    public int waitQueue (@RequestBody UserRequestDto userRequestDto){
        if(user1!=null && user2 != null){
            ChatRoomVO room;
                //  방이  이미 존재하는경우,
                //  중복해서 생성하지 않고, 해당 방으로 들어간다
            for(ChatRoomVO ro : roomService.findAllRooms()){
                if(ro.getUser1_id().equals(user1.getId()) && ro.getUser2_id().equals(user2.getId()) ||
                        ro.getUser1_id().equals(user2.getId()) && ro.getUser2_id().equals(user1.getId())){
                    user1 = null;
                    user2 = null;
                    return ro.getRoom_code();
                }
            }
            room=roomService.createChatRoomVO(user1.getId(), user2.getId());
            user1 = null;
            user2 = null;
            return room.getRoom_code();
        }else if(user1 == null && user2 == null){
            //  두번쨰 큐에 있는 유저도 redirect 해주기 위함
            //  새로 생성 된 경우, ArrayList 의 맨 마지막에 있으므로,
            //  Collection.revert 메소드로 뒤집어준다
            //  가장 최신에 생성된 채팅방이 참가하고자하는 방이다.
            for(ChatRoomVO ro : roomService.findAllRooms()){
                if(ro.getUser1_id().equals(userRequestDto.getId()) || ro.getUser2_id().equals(userRequestDto.getId())){
                    return ro.getRoom_code();
                }
            }
            return 0;
        }else
            return 0;
    }
}