package org.krainet.timetracker.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krainet.timetracker.Service.RecordService;
import org.krainet.timetracker.Service.TaskService;
import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.Record;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/records")
@AllArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping
    public String viewTasks(Model model) {
        List<Task> tasks = taskService.readAll();
        model.addAttribute("tasks", tasks);
        return "task-tracker";
    }


    @PostMapping("/start")
    public ResponseEntity<?> startRecord(@RequestParam Long userId, @RequestParam Long taskId) {
        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Task task = taskService.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        Optional<Record> existingRecord = recordService.getCurrentRecord(user, task);
        long recordedTime = existingRecord.map(Record::getTimeRecord).orElse(0L);

        return ResponseEntity.ok(recordedTime);
    }


    @PostMapping("/save/{taskId}/{userId}")
    public ResponseEntity<?> saveRecord(@PathVariable Long taskId, @RequestBody Map<String, Object> requestData, @PathVariable Long userId) {
        String startTimeStr = ((String) requestData.get("startTime")).replace("Z", "");
        String endTimeStr = ((String) requestData.get("endTime")).replace("Z", "");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr);

        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Task task = taskService.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        Long elapsedSeconds = ((Number) requestData.get("elapsed")).longValue();

        if (recordService.findById(taskId).isPresent()) {
            Record existingRecord = recordService.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Project not found"));
            existingRecord.setUser(user);
            existingRecord.setTask(task);
            existingRecord.setStartTime(startTime);
            existingRecord.setEndTime(endTime);
            existingRecord.setTimeRecord(elapsedSeconds);

            recordService.update(existingRecord);
        } else {
            recordService.create(recordService.create(Record.builder()
                    .id(taskId)
                    .timeRecord(elapsedSeconds)
                    .startTime(startTime)
                    .endTime(endTime)
                    .task(task)
                    .user(user)
                    .build()));
        }

        return ResponseEntity.ok("Time saved successfully");
    }


    @PostMapping("/stop/{id}")
    public String stopRecord(@PathVariable String id) {

        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Record>> readById(@PathVariable Long id) {
        return new ResponseEntity<>(recordService.findById(id), HttpStatus.OK);

    }


    @PutMapping
    private ResponseEntity<Record> update(@RequestBody Record record) {
        return new ResponseEntity<>(recordService.update(record), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private HttpStatus delete(@PathVariable Long id) {
        recordService.delete(id);
        return HttpStatus.OK;
    }

}
