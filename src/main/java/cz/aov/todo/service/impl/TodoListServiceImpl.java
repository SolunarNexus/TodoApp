package cz.aov.todo.service.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.service.TodoListService;
import cz.aov.todo.storage.WorkItemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<WorkItemModel> addWorkItems(List<WorkItemModel> list) {
        return storage.saveAll(list);
    }

    @Override
    public WorkItemModel getWorkItem(Long id) {
        return storage.get(id);
    }

    @Override
    public WorkItemModel updateWorkItem(WorkItemModel workItem) {
        return storage.update(workItem);
    }

    @Override
    public void removeWorkItem(Long id) {
        storage.delete(id);
    }

    @Override
    public Map<LocalDate, List<WorkItemModel>> findCompletedWorkItemsByDays() {
        List<WorkItemModel> completed = storage.filter(item -> item.getCompletedAt() != null);
        Map<LocalDate, List<WorkItemModel>> result = new HashMap<>();

        completed.forEach(item -> {
            LocalDate day = item.getCompletedAt().toLocalDate();

            if (!result.containsKey(day))
                result.put(day, new ArrayList<>());

            result.get(day).add(item);
        });

        return result;
    }
}
