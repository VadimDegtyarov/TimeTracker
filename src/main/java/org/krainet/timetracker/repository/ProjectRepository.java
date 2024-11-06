package org.krainet.timetracker.repository;

import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.Record;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<List<Project>> findAllById(Long id);
}
