package cz.aov.todo.service.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.service.TodoListService;
import cz.aov.todo.storage.WorkItemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoListServiceImpl implements TodoListService {
    private final WorkItemStorage storage;

    @Autowired
    public TodoListServiceImpl(WorkItemStorage storage) {
        this.storage = storage;
    }

    @Override
    public WorkItemModel addWorkItem(WorkItemModel workItem) {
        return storage.save(workItem);
    }
}
