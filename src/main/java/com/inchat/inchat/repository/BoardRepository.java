package com.inchat.inchat.repository;

import com.inchat.inchat.domain.BoardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardVO, Integer> {
}
