package cz.aov.todo.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkItemsDtoBulkLoad
{
	List<WorkItemDtoCreate> workItems;
}
