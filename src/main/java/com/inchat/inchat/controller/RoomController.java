package com.inchat.inchat.controller;

import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.service.RoomService;
import com.inchat.inchat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    @Autowired
    private RoomService roomService = new RoomService();
    @Autowired
    private UserService Userservice = new UserService();

    //  목록 조회
    @GetMapping(value = "/roomList")
    public ModelAndView rooms(){
        ModelAndView modelAndView = new ModelAndView("roomList");
        modelAndView.addObject("ArrayList",roomService.findAllRooms());
        return modelAndView;
    }

    @PostMapping(value = "/rooms")
    public String create(@RequestBody UserRequestDto userRequestDto1, @RequestBody UserRequestDto userRequestDto2, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("room_code",roomService.createChatRoomVO(userRequestDto1.getId(), userRequestDto2.getId()));
        return "redirect:/chatPage/rooms";
    }

    @GetMapping("/rooms")
    public void getRoom(int room_code, Model model){
        model.addAttribute("rooms",roomService.findRoomByCode(room_code));
    }
}
