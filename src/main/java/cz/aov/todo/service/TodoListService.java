package cz.aov.todo.service;

import cz.aov.todo.model.WorkItemModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TodoListService {
    /**
     * Adds a work item to the to-do list.
     *
     * @param workItem the work item to add.
     * @return the added work item or null on error.
     */
    WorkItemModel addWorkItem(WorkItemModel workItem);

    /**
     * Adds a list of work items to the to-do list.
     *
     * @param list the list of work items.
     * @return the list of added work items.
     */
    List<WorkItemModel> addWorkItems(List<WorkItemModel> list);

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

    /**
     * Find completed work items grouped by days.
     *
     * @return the map of work items where the key is the day and the value is the list of work items completed on that day.
     */
    Map<LocalDate, List<WorkItemModel>> findCompletedWorkItemsByDays();

    /**
     * Find complete work items on specific day.
     *
     * @param date the day of interest.
     * @return list of work items completed on given day.
     */
    List<WorkItemModel> findCompleteWorkItemsForDate(LocalDate date);
}
