package com.inchat.inchat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardDTO {
    private int code;
    private String writer;
    private String title;
    private String contents;
    private int viewCount;

    public void setCode(int code){
        this.code =code;
    }
    public void addViewCount(){
        this.viewCount++;
    }
}
