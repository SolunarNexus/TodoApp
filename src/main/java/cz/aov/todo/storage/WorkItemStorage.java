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

    WorkItemModel get(Long id);
}
