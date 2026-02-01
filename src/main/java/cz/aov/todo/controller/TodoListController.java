package cz.aov.todo.controller;

import cz.aov.todo.controller.dto.WorkItemDtoCreate;
import cz.aov.todo.controller.dto.WorkItemDtoUpdate;
import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.service.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)})
    public WorkItemModel addWorkItem(WorkItemDtoCreate workItemDto) {
        return todoListService.addWorkItem(new WorkItemModel(workItemDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve work item from the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work item retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemModel.class))}),
            @ApiResponse(responseCode = "404", description = "Work item not found", content = @Content),
    })
    public WorkItemModel getWorkItem(@PathVariable Long id) {
        WorkItemModel model = todoListService.getWorkItem(id);
        if (model == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Work item not found");

        return model;
    }

    @PutMapping("/update")
    @Operation(summary = "Update work item to the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work item updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
    })
    public WorkItemModel updateWorkItem(WorkItemDtoUpdate workItemDto) {
        WorkItemModel updateWorkItem;

        try {
            updateWorkItem = todoListService.updateWorkItem(new WorkItemModel(workItemDto));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return updateWorkItem;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove work item from the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work item removed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
    })
    public void removeWorkItem(@PathVariable Long id) {

        try {
            todoListService.removeWorkItem(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
