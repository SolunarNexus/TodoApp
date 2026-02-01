package cz.aov.todo.storage.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.storage.WorkItemStorage;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Log
@Component
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

    private boolean workItemNotPresent(Long id) {
        return !workItems.containsKey(id);
    }

    private void logAndThrowIllegalArgumentException(String message) {
        log.severe(message);
        throw new IllegalArgumentException(message);
    }
}
