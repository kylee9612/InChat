package com.inchat.inchat.util;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass                                   //  나를 상속 받은 자식 클래스에게 멤버 변수들을 -> 컬럼으로 부여
@EntityListeners(AuditingEntityListener.class)      //  <- 실시간으로 엔티티의 변화 상황을 지켜보게 됌
public class Timestamp {
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
