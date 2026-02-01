package cz.aov.todo.service;

import cz.aov.todo.model.WorkItemModel;

public interface TodoListService {
    WorkItemModel addWorkItem(WorkItemModel workItem);

    WorkItemModel getWorkItem(Long id);
}
