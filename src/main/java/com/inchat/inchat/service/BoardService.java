package com.inchat.inchat.service;

import com.inchat.inchat.domain.BoardDTO;
import com.inchat.inchat.domain.BoardVO;
import com.inchat.inchat.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    BoardRepository repository;

    public List<BoardVO> findAllBoardOrderByCode() {
        return repository.findAll(Sort.by("code"));
    }

    public BoardVO findBoardByCode(int code) {
        Optional<BoardVO> result = repository.findById(code);
        return result.orElse(null);
    }

    public void createBoard(BoardDTO boardDTO) {
        BoardVO boardVO = new BoardVO(boardDTO);
        System.out.println(boardVO);
        repository.save(boardVO);
    }

    public void updateBoard(BoardDTO board) {
        BoardVO boardVO = findBoardByCode(board.getCode());
        boardVO = boardVO.update(board);
        if (boardVO != null) {
            repository.save(boardVO);
        }
    }

    public void updateViewCount(BoardDTO board) {
        board.addViewCount();
        updateBoard(board);
    }

    public void updateViewCount(BoardVO board) {
        board.addViewCount();
        repository.save(board);
    }

    public boolean deleteBoard(BoardDTO board){
        if(board!=null && board.getCode() != 0) {
            repository.deleteById(board.getCode());
            return true;
        }
        return false;
    }
}
