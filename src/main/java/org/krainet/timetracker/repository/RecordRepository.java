package org.krainet.timetracker.repository;

import org.krainet.timetracker.model.Record;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record,Long> {
    Optional<Record> findActiveRecordByUserAndTask(User user, Task task);

}
