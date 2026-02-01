package cz.aov.todo.storage;

import cz.aov.todo.model.WorkItemModel;

public interface WorkItemStorage {
    /**
     * Persist a single work item.
     *
     * @param workItem a work item to persist.
     * @return the persisted work item.
     */
    WorkItemModel save(WorkItemModel workItem);

    /**
     * Retrieve a single work item.
     *
     * @param id the id of the work item to retrieve.
     * @return the work item or null if not found.
     */
    WorkItemModel get(Long id);

    /**
     * Update a single work item.
     *
     * @param workItem the work item to update.
     * @return the updated work item.
     */
    WorkItemModel update(WorkItemModel workItem);
}
