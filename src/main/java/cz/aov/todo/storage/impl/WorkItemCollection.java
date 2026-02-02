package cz.aov.todo.storage.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.storage.WorkItemStorage;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Log
@Component
@Profile("inmemory-collection")
public class WorkItemCollection implements WorkItemStorage {
    private static final AtomicLong count = new AtomicLong(0);
    private final Map<Long, WorkItemModel> workItems = new HashMap<>();

    @Override
    public WorkItemModel save(WorkItemModel workItem) {
        workItem.setId(count.incrementAndGet());

        try {
            workItems.put(workItem.getId(), workItem);
        } catch (Exception e) {
            log.severe(MessageFormat.format("Failed to save work item {0}. Reason: {1}", workItem, e.getMessage()));
            throw e;
        }
        return workItem;
    }

    @Override
    public List<WorkItemModel> saveAll(List<WorkItemModel> list) {
        list.forEach(item -> item.setId(count.incrementAndGet()));

        try {
            workItems.putAll(list.stream().collect(Collectors.toMap(WorkItemModel::getId, item -> item)));
        } catch (Exception e) {
            log.severe(MessageFormat.format("Bulk load failed. Reason: {0}", e.getMessage()));
            throw e;
        }
        return list;
    }

    @Override
    public WorkItemModel get(Long id) {
        if (workItemNotPresent(id))
            logAndThrowIllegalArgumentException(MessageFormat.format("Work item with id [{0}] not found.", id));

        return workItems.get(id);
    }

    @Override
    public WorkItemModel update(WorkItemModel workItem) {
        if (workItemNotPresent(workItem.getId()))
            logAndThrowIllegalArgumentException(MessageFormat.format("Work item with id [{0}] cannot be updated. Reason: not found.", workItem.getId()));

        workItems.put(workItem.getId(), workItem);
        return workItem;
    }

    @Override
    public void delete(Long id) {
        if (workItemNotPresent(id))
            logAndThrowIllegalArgumentException(MessageFormat.format("Work item with id [{0}] cannot be deleted. Reason: not found.", id));

        workItems.remove(id);
    }

    @Override
    public List<WorkItemModel> filter(Predicate<WorkItemModel> predicate) {
        return workItems.values().stream().filter(predicate).toList();
    }

    private boolean workItemNotPresent(Long id) {
        return !workItems.containsKey(id);
    }

    private void logAndThrowIllegalArgumentException(String message) {
        log.severe(message);
        throw new IllegalArgumentException(message);
    }
}
