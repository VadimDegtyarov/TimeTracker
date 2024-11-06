package org.krainet.timetracker.Service;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.dto.TaskDTO;
import org.krainet.timetracker.dto.UserDTO;
import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.Record;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.krainet.timetracker.repository.TaskRepository;
import org.krainet.timetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final RecordService recordService;

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }


    public Task create(TaskDTO dto) {
        User user = userService.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Project project = projectService.findById(dto.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));
        return taskRepository.save(Task.builder()
                .taskName(dto.getTaskName())
                .taskInfo(dto.getTaskInfo())
                .createdAt(LocalDateTime.now())
                .user(user)
                .project(project)
                .build());


    }

    public List<Task> readAll() {

        return taskRepository.findAll();
    }
    public List<Task> findAllByUserIdAndProjectId(Long userId, Long projectId) {
        return taskRepository.findAllByUserIdAndProjectId(userId, projectId);
    }
    public Task update(Task task) {

        return taskRepository.save(task);
    }

    public void delete(Long id) {

        taskRepository.deleteById(id);
    }
}
