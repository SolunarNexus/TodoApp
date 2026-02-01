package cz.aov.todo.model;

import cz.aov.todo.controller.dto.WorkItemDtoCreate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkItemModel {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime completedAt;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public WorkItemModel(WorkItemDtoCreate dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = Status.TODO;
        this.priority = dto.getPriority();
    }

    public enum Status {
        TODO, IN_PROGRESS, COMPLETED
    }

    public enum Priority {
        LOWEST, LOW, MEDIUM, HIGH, HIGHEST
    }
}
