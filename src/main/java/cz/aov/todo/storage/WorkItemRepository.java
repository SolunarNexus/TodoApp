package cz.aov.todo.storage;

import cz.aov.todo.model.WorkItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkItemRepository extends JpaRepository<WorkItemModel, Long> {
}
