package cz.aov.todo.storage.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.storage.WorkItemStorage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
}
