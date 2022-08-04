package com.inchat.inchat.repository;

import com.inchat.inchat.domain.ChatMessageDTO;
import com.inchat.inchat.domain.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessageDTO, Integer> {
    @Modifying
    @Query(value = "update ChatMessageDTO c set " +
            "c.writer = :newNick where c.writer = :origin")
    public void updateUserNick(String origin, String newNick);
}
