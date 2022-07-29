package com.inchat.inchat.repository;

import com.inchat.inchat.domain.ChatMessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessageDTO, Integer> {

}
