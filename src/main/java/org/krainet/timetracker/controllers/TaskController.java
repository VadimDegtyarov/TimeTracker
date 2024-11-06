package org.krainet.timetracker.controllers;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.Exception.GlobalExceptionHandler;
import org.krainet.timetracker.Service.ProjectService;
import org.krainet.timetracker.Service.RecordService;
import org.krainet.timetracker.Service.TaskService;
import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.dto.TaskDTO;
import org.krainet.timetracker.model.Record;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller


@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final RecordService recordService;
    private final UserService userService;

    @PostMapping("/create-task")
    public String create(@ModelAttribute TaskDTO dto) {
        taskService.create(dto);

        return "redirect:/user/" + dto.getUserId() + "/project/" + dto.getProjectId();
    }

    @GetMapping("/create")
    public String createTask(Model model) {
        TaskDTO dto = new TaskDTO();
        model.addAttribute("task", dto);

        return "create-task";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> readById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);

    }

    @GetMapping
    private ResponseEntity<List<Task>> readAll() {
        return new ResponseEntity<>(taskService.readAll(), HttpStatus.OK);
    }

    @PutMapping
    private ResponseEntity<Task> update(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.update(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private HttpStatus delete(@PathVariable Long id) {
        taskService.delete(id);
        return HttpStatus.OK;
    }

}
