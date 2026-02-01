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
            log.severe(MessageFormat.format("Failed to save work item {0}, reason {1}", workItem, e.getMessage()));
            throw e;
        }
        return workItem;
    }
}
