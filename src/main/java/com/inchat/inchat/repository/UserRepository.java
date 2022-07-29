package com.inchat.inchat.repository;

import com.inchat.inchat.domain.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <UserVO,String>{

}
