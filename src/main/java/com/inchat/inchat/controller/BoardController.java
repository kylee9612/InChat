package com.inchat.inchat.controller;

import com.inchat.inchat.domain.BoardDTO;
import com.inchat.inchat.domain.BoardVO;
import com.inchat.inchat.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/v2/create-board")
    public boolean createBoard(@RequestBody BoardDTO boardDTO) {
        boardService.createBoard(boardDTO);
        return true;
    }

    @PostMapping("/v2/update-board")
    public boolean updateBoard(@RequestBody BoardDTO boardDTO) {
        if (boardService.findBoardByCode(boardDTO.getCode()) != null) {
            boardService.updateBoard(boardDTO);
            return true;
        }
        return false;
    }

    @PostMapping("/v2/get-board")
    public BoardVO getBoard(@RequestBody BoardDTO boardDTO) {
        boardService.updateViewCount(boardDTO);
        return boardService.findBoardByCode(boardDTO.getCode());
    }

    @PostMapping("/v2/delete-board")
    public boolean deleteBoard(@RequestBody BoardDTO boardDTO){
        return boardService.deleteBoard(boardDTO);
    }
}
