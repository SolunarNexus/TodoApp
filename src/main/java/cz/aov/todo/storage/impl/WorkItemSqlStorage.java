package cz.aov.todo.storage.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.storage.WorkItemRepository;
import cz.aov.todo.storage.WorkItemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@Profile("mysql")
public class WorkItemSqlStorage implements WorkItemStorage {
    private final WorkItemRepository repository;

    @Autowired
    public WorkItemSqlStorage(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public WorkItemModel save(WorkItemModel workItem) {
        return repository.save(workItem);
    }

    @Override
    public List<WorkItemModel> saveAll(List<WorkItemModel> list) {
        return repository.saveAll(list);
    }

    @Override
    public WorkItemModel get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public WorkItemModel update(WorkItemModel workItem) {
        return repository.save(workItem);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<WorkItemModel> filter(Predicate<WorkItemModel> predicate) {
        // TODO: not scalable, needs refactor
        return repository.findAll().stream().filter(predicate).toList();
    }
}
