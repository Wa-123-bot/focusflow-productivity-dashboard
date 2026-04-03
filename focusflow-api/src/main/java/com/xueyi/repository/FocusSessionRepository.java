package com.xueyi.repository;

import com.xueyi.model.FocusSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {
    List<FocusSession> findByStartTimeAfter(LocalDateTime startTime);
}