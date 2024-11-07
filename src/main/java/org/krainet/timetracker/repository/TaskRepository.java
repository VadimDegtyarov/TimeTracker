package org.krainet.timetracker.repository;

import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUserIdAndProjectId(Long userId, Long projectId);
}
