package com.inchat.inchat.domain;

import com.inchat.inchat.util.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name="user")
public class UserVO extends Timestamp{
    @Id
    private String id;

    @Column(nullable = false)
    private String pw,nickname;

    public UserVO(UserRequestDto userRequestDto){
        this.id = userRequestDto.getId();
        this.pw = userRequestDto.getPw();
        this.nickname = userRequestDto.getNickname();
    }

    public void update(UserRequestDto userRequestDto){
        if(userRequestDto.getNickname()!= null && !userRequestDto.getNickname().equals(""))
            this.nickname = userRequestDto.getNickname();
        if(userRequestDto.getPw()!=null && !userRequestDto.getPw().equals(""))
            this.pw = userRequestDto.getPw();
    }
}
