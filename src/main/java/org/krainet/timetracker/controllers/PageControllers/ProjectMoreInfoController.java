package org.krainet.timetracker.controllers.PageControllers;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.Service.ProjectService;
import org.krainet.timetracker.Service.TaskService;
import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
public class ProjectMoreInfoController {
    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/user/{userId}/project/{projectId}")
    public String getProjectMoreInfo(@PathVariable Long userId, @PathVariable Long projectId, Model model) {
        List<Task> tasks = taskService.findAllByUserIdAndProjectId(userId, projectId);
        Optional<User> user = userService.findById(userId);
        Optional<Project> project = projectService.findById(projectId);

        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("project", project.orElse(null));

        return "ProjectInfo";
    }


}
