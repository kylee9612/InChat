package com.inchat.inchat.domain;

import com.inchat.inchat.util.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table (name="board")
public class BoardVO extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("1")
    private int code;
    private String writer;
    private String title;
    private String contents;
    private int viewCount;

    public BoardVO(BoardDTO boardDTO){
        this.code = boardDTO.getCode();
        this.writer = boardDTO.getWriter();
        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
        this.viewCount = boardDTO.getViewCount();
    }

    public BoardVO update(BoardDTO board){
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.viewCount = board.getViewCount();
        return this;
    }

    public void addViewCount(){
        this.viewCount++;
    }
}
