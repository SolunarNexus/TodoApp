package cz.aov.todo.storage;

import cz.aov.todo.model.WorkItemModel;

public interface WorkItemStorage {
    WorkItemModel save(WorkItemModel workItem);
}
