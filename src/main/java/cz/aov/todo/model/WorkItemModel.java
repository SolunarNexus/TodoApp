package cz.aov.todo.model;

import cz.aov.todo.controller.dto.WorkItemDtoCreate;
import cz.aov.todo.controller.dto.WorkItemDtoUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
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

    public WorkItemModel(WorkItemDtoUpdate dto){
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = dto.getStatus();
        this.priority = dto.getPriority();
        this.completedAt = dto.getCompletedAt();
    }

    public enum Status {
        TODO, IN_PROGRESS, COMPLETED
    }

    public enum Priority {
        LOWEST, LOW, MEDIUM, HIGH, HIGHEST
    }
}
