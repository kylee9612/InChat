package com.inchat.inchat.controller;

import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.domain.UserVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private DBController dbc = new DBController();

    @PostMapping("v1/search/user")
    public UserVO getUser(@RequestBody UserRequestDto userRequestDto) {
        UserVO userVO = dbc.getUser(userRequestDto.getId());
        if (userVO != null)
            return userVO;
        else
            return null;
    }

    @PostMapping("/v1/adduser")
    public boolean addUser(@RequestBody UserRequestDto userRequestDto){
        UserVO user = new UserVO(userRequestDto.getId(), userRequestDto.getPw(), userRequestDto.getNick());
        return dbc.addUser(user);
    }

    @PostMapping("/v1/login-user")
    public UserVO loginUser(@RequestBody UserRequestDto userRequestDto){
        return dbc.loginUser(userRequestDto.getId(), userRequestDto.getPw());
    }
}