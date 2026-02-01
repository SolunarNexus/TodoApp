package cz.aov.todo.controller.dto;

import cz.aov.todo.model.WorkItemModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkItemDtoUpdate {
    private final Long id;
    private final String title;
    private final String description;
    private final WorkItemModel.Status status;
    private final WorkItemModel.Priority priority;
    private final LocalDateTime completedAt;
}
