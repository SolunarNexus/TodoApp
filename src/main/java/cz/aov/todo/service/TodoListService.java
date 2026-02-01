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

    /**
     * Retrieves a work item from the to-do list.
     *
     * @param id the id of the work item to retrieve.
     * @return the work item or null if not found.
     */
    WorkItemModel getWorkItem(Long id);

    /**
     * Updates a work item in the to-do list.
     * @param workItem the work item to update.
     * @return the updated work item.
     */
    WorkItemModel updateWorkItem(WorkItemModel workItem);

    /**
     * Removes a work item from the to-do list.
     *
     * @param id the id of the work item to remove.
     */
    void removeWorkItem(Long id);
}
