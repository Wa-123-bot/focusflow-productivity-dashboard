package com.xueyi.controller;

import com.xueyi.model.FocusSession;
import com.xueyi.repository.FocusSessionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/focus")
@CrossOrigin(origins = "http://localhost:5173")
public class FocusSessionController {

    private final FocusSessionRepository sessionRepository;

    public FocusSessionController(FocusSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/sessions")
    public List<FocusSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    @PostMapping("/session")
    public FocusSession createSession(@RequestBody Map<String, String> body) {
        Long taskId = Long.parseLong(body.get("taskId"));
        LocalDateTime startTime = LocalDateTime.parse(body.get("startTime"));
        LocalDateTime endTime = LocalDateTime.parse(body.get("endTime"));

        int minutes = (int) ChronoUnit.MINUTES.between(startTime, endTime);

        FocusSession session = new FocusSession(taskId, startTime, endTime, minutes);
        return sessionRepository.save(session);
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        List<FocusSession> allSessions = sessionRepository.findAll();
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();

        int todayMinutes = allSessions.stream()
                .filter(s -> !s.getStartTime().isBefore(todayStart))
                .mapToInt(FocusSession::getDurationMinutes)
                .sum();

        Map<String, Object> result = new HashMap<>();
        result.put("todayMinutes", todayMinutes);
        result.put("sessionCount", allSessions.size());
        return result;
    }
}