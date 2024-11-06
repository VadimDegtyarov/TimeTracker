package org.krainet.timetracker.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class RecordDTO {
    private Long userId;
    private Long taskId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration timeRecord;
}