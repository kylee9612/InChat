package com.inchat.inchat.repository;

import com.inchat.inchat.domain.ChatRoomVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<ChatRoomVO, Integer> {
}
