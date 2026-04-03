package com.xueyi.controller;

import com.xueyi.model.FocusSession;
import com.xueyi.model.Task;
import com.xueyi.model.TaskStatus;
import com.xueyi.repository.FocusSessionRepository;
import com.xueyi.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyticsController {

    private final FocusSessionRepository sessionRepository;
    private final TaskRepository taskRepository;

    public AnalyticsController(FocusSessionRepository sessionRepository, TaskRepository taskRepository) {
        this.sessionRepository = sessionRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/weekly")
    public List<Map<String, Object>> getWeeklyData() {
        List<FocusSession> sessions = sessionRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            int totalMinutes = sessions.stream()
                    .filter(s -> s.getStartTime().toLocalDate().equals(day))
                    .mapToInt(FocusSession::getDurationMinutes)
                    .sum();

            Map<String, Object> row = new HashMap<>();
            row.put("date", day.toString());
            row.put("minutes", totalMinutes);
            result.add(row);
        }

        return result;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        List<Task> tasks = taskRepository.findAll();
        long doneCount = tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        int totalCount = tasks.size();
        int completionRate = totalCount == 0 ? 0 : (int) ((doneCount * 100.0) / totalCount);

        int totalFocusMinutes = sessionRepository.findAll().stream()
                .mapToInt(FocusSession::getDurationMinutes)
                .sum();

        Map<String, Object> result = new HashMap<>();
        result.put("taskCount", totalCount);
        result.put("doneCount", doneCount);
        result.put("completionRate", completionRate);
        result.put("totalFocusMinutes", totalFocusMinutes);
        return result;
    }
}