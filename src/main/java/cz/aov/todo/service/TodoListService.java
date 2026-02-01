package cz.aov.todo.service;

import cz.aov.todo.model.WorkItemModel;

public interface TodoListService {
    /**
     * Adds a work item to the to-do list.
     *
     * @param workItem the work item to add.
     * @return the added work item or null on error.
     */
    WorkItemModel addWorkItem(WorkItemModel workItem);

    WorkItemModel getWorkItem(Long id);
}
