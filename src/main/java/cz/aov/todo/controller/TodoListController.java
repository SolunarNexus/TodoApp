package cz.aov.todo.controller;

import cz.aov.todo.controller.dto.WorkItemDtoCreate;
import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.service.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/todo-list")
public class TodoListController {
    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("/add")
    @Operation(summary = "Add work item to the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work item added successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public WorkItemModel addWorkItem(WorkItemDtoCreate workItemDto) {
        return todoListService.addWorkItem(new WorkItemModel(workItemDto));
    }
}
