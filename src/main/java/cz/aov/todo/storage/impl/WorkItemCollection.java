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
        workItems.put(workItem.getId(), workItem);
        return workItem;
    }

    @Override
    public WorkItemModel get(Long id) {
        WorkItemModel workItem = workItems.get(id);
        if (workItem == null)
            log.severe(MessageFormat.format("Work item with id [{0}] not found", id));

        return workItem;
    }
}
