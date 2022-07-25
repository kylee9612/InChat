package com.inchat.inchat.service;

import com.inchat.inchat.domain.UserRepository;
import com.inchat.inchat.domain.UserRequestDto;
import com.inchat.inchat.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //  CRUD

    //  1. Create
    public void createUser(UserRequestDto userRequestDto){
        UserVO user = new UserVO(userRequestDto);
        userRepository.save(user);
    }

    //  2. Read
    public UserVO readUser(UserRequestDto userRequestDto){
        UserVO result = userRepository.findById(userRequestDto.getId()).orElse(null);
        if(result!= null && result.getId().equals(userRequestDto.getId())){
            return result;
        }
        return null;
    }

    //  3. Update
    @Transactional // <- 조회된 기존 객체를 갱신
    public UserVO updateUser(UserRequestDto userRequestDto){
        UserVO user = readUser(userRequestDto);
        user.update(userRequestDto);
        userRepository.save(user);
        return user;
    }

    //  4. Delete
    @Transactional
    public void deleteUser(UserRequestDto userRequestDto){
        UserVO user = readUser(userRequestDto);
        userRepository.deleteById(user.getId());
    }
}
