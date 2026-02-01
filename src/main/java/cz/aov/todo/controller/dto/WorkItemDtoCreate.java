package cz.aov.todo.controller.dto;

import cz.aov.todo.model.WorkItemModel;
import lombok.Data;

@Data
public class WorkItemDtoCreate {
    private final String title;
    private final String description;
    private final WorkItemModel.Priority priority;
}
