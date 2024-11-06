package org.krainet.timetracker.Service;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.dto.ProjectDTO;
import org.krainet.timetracker.dto.UserDTO;
import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.User;
import org.krainet.timetracker.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }



    public Project create(ProjectDTO dto) {
        User user = userService.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectRepository.save(Project.builder()
                .projectName(dto.getProjectName())
                .projectInfo(dto.getProjectInfo())
                .timeInProject(dto.getTimeInProject())
                .user(user)
                .build());
    }

    public List<Project> readAll() {

        return projectRepository.findAll();
    }

    public Project update(Project project) {

        return projectRepository.save(project);
    }

    public void delete(Long id) {

        projectRepository.deleteById(id);
    }
}
