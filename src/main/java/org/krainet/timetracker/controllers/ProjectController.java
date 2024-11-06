package org.krainet.timetracker.controllers;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.Exception.GlobalExceptionHandler;
import org.krainet.timetracker.Service.ProjectService;
import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.dto.ProjectDTO;
import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller


@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping("/create-project")
    public String create(ProjectDTO dto) {
        projectService.create(dto);
        return "redirect:/";
    }


    @GetMapping("/create")
    public String createProject(Model model, Principal principal) {
        User user = userService.findByUserEmail(principal.getName()).orElseThrow();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setUserId(user.getId());
        model.addAttribute("project",projectDTO);
        return "create-project";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> readById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.findById(id), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Project>> readAll() {
        return new ResponseEntity<>(projectService.readAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project) {
        return new ResponseEntity<>(projectService.update(project), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        projectService.delete(id);
        return HttpStatus.OK;
    }


}
