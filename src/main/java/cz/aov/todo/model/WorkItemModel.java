package cz.aov.todo.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkItemModel {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private enum Status {
        TODO, IN_PROGRESS, COMPLETED
    }

    private enum Priority {
        LOWEST, LOW, MEDIUM, HIGH, HIGHEST
    }
}
