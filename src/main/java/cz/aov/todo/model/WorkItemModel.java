package cz.aov.todo.model;

import cz.aov.todo.controller.dto.WorkItemDtoCreate;
import cz.aov.todo.controller.dto.WorkItemDtoUpdate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "work_items")
public class WorkItemModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDateTime completedAt;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public WorkItemModel(WorkItemDtoCreate dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.status = Status.TODO;
        this.priority = dto.getPriority();
    }

    public WorkItemModel(WorkItemDtoUpdate dto) {
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

    public boolean isCompleted() {
        return this.completedAt != null;
    }
}
