package org.krainet.timetracker.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;
    private String taskName;
    private String taskInfo;
    private LocalDateTime createdAt;
    private Long userId;
    private Long projectId;
}
