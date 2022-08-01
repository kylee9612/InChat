package com.inchat.inchat.repository;

import com.inchat.inchat.domain.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardVO, Integer> {
    @Modifying
    @Query(value = "update BoardVO board set board.title = :title, board.contents = :contents where board.code = :code")
    public void updateBoard(String title, String contents, int code);
}
