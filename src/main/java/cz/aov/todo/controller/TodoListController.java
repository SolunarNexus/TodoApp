package cz.aov.todo.controller;

import cz.aov.todo.controller.dto.WorkItemDtoCreate;
import cz.aov.todo.controller.dto.WorkItemDtoUpdate;
import cz.aov.todo.controller.dto.WorkItemsDtoBulkLoad;
import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.service.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/todo-list")
public class TodoListController {
    private final TodoListService todoListService;

    @Autowired
    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add work item to the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work item added successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public WorkItemModel addWorkItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Work item to create", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemDtoCreate.class), examples = @ExampleObject(value = "{ \"title\": \"New work item\", \"description\": \"First do 1, then 2, and finally 3.\", \"priority\": \"MEDIUM\" }")))
            @RequestBody WorkItemDtoCreate dto
    ) {
        return todoListService.addWorkItem(new WorkItemModel(dto));
    }

    @PostMapping("/add-bulk")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add work items to the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work items added successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemsDtoBulkLoad.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public List<WorkItemModel> addWorkItems(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Work items to create", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemsDtoBulkLoad.class), examples = @ExampleObject(value = "{ \"workItems\": [ {\"title\": \"First item\", \"description\": \"Do this.\", \"priority\": \"MEDIUM\"}, {\"title\": \"Second item\", \"description\": \"Do that.\", \"priority\": \"LOW\"} ]}")))
            @RequestBody WorkItemsDtoBulkLoad dto
    ) {
        return todoListService.addWorkItems(dto.getWorkItems().stream().map(WorkItemModel::new).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve work item from the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work item retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemModel.class))}),
            @ApiResponse(responseCode = "404", description = "Work item not found", content = @Content),
    })
    public WorkItemModel getWorkItem(@Parameter(description = "id of work item to be retrieved") @PathVariable Long id) {
        try {
            return todoListService.getWorkItem(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update work item to the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work item updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Work item not found", content = @Content)
    })
    public WorkItemModel updateWorkItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Work item to update", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkItemDtoUpdate.class), examples = @ExampleObject(value = "{ \"id\": 1, \"title\": \"New work item\", \"description\": \"First do 1, then 2, and finally 3.\", \"status\": \"IN_PROGRESS\", \"priority\": \"MEDIUM\", \"completedAt\": \"2026-01-01T00:00:00.000\" }")))
            @Parameter(name = "WorkItem") @RequestBody WorkItemDtoUpdate dto
    ) {
        WorkItemModel updateWorkItem;

        try {
            updateWorkItem = todoListService.updateWorkItem(new WorkItemModel(dto));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return updateWorkItem;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove work item from the todo list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Work item removed successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Work item not found", content = @Content),
    })
    public void removeWorkItem(@PathVariable Long id) {
        try {
            todoListService.removeWorkItem(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/completed")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve completed work items grouped by days")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Work items found", content = @Content),
    })
    public Map<LocalDate, List<WorkItemModel>> retrieveCompletedWorkItemsByDays() {
        return todoListService.findCompletedWorkItemsByDays();
    }
}
