package cz.aov.todo.service.impl;

import cz.aov.todo.model.WorkItemModel;
import cz.aov.todo.storage.WorkItemStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TodoListServiceImplTest {
    @Mock
    WorkItemStorage storage;

    @InjectMocks
    TodoListServiceImpl service;

    @Test
    void responseShouldBeEmptyWhenNoWorkItemsCompleted() {
        // ARRANGE
        Mockito.when(storage.filter(any())).thenReturn(List.of());

        // ACT
        Map<LocalDate, List<WorkItemModel>> result = service.findCompletedWorkItemsByDays();

        // ASSERT
        assertThat(result).isEmpty();
    }

    @Test
    void completedItemsShouldBeInCorrespondingDayBuckets() {
        // ARRANGE
        Mockito.when(storage.filter(any())).thenReturn(List.of(
                WorkItemModel.builder().completedAt(LocalDateTime.now()).build(),
                WorkItemModel.builder().completedAt(LocalDateTime.now().minusDays(1)).build(),
                WorkItemModel.builder().completedAt(LocalDateTime.now().minusDays(1)).build()
        ));

        // ACT
        Map<LocalDate, List<WorkItemModel>> result = service.findCompletedWorkItemsByDays();

        // ASSERT
        assertThat(result).isNotEmpty();
        assertThat(result.keySet()).hasSize(2);
        assertThat(result.values()).allSatisfy(list -> assertThat(list).isNotEmpty());
        assertThat(result.entrySet())
                .allSatisfy(entry ->
                        assertThat(entry.getValue()).allSatisfy(item ->
                                assertThat(item.getCompletedAt().toLocalDate()).isEqualTo(entry.getKey())));
    }

    @Test
    void completedItemsShouldBeIncludedForGivenDay() {
        // ARRANGE
        LocalDate date = LocalDate.now();
        Mockito.when(storage.filter(any())).thenReturn(List.of(
                WorkItemModel.builder().completedAt(date.atStartOfDay()).build(),
                WorkItemModel.builder().completedAt(date.atStartOfDay().plusHours(3)).build(),
                WorkItemModel.builder().completedAt(date.atStartOfDay().plusMinutes(15)).build()
        ));

        // ACT
        List<WorkItemModel> result = service.findCompleteWorkItemsForDate(date);

        // ASSERT
        assertThat(result)
                .hasSize(3)
                .allSatisfy(item ->
                        assertThat(item.getCompletedAt().toLocalDate()).isEqualTo(date));
    }
}
