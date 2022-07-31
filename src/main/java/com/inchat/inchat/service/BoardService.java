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

    public BoardVO findBoardByCode(BoardDTO boardDTO) {
        Optional<BoardVO> result = repository.findById(boardDTO.getCode());
        return result.orElse(null);
    }

    public void createBoard(BoardDTO boardDTO) {
        BoardVO boardVO = new BoardVO(boardDTO);
        System.out.println(boardVO);
        repository.save(boardVO);
    }

    public void updateBoard(BoardDTO board) {
        BoardVO boardVO = findBoardByCode(board);
        boardVO = boardVO.update(board);
        if (boardVO != null)
            repository.save(boardVO);
    }

    public void updateViewCount(BoardDTO board) {
        board.addViewCount();
        updateBoard(board);
    }
}
