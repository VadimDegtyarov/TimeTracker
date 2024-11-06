package org.krainet.timetracker.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.Duration;

@Data
public class ProjectDTO {

    private String projectName;
    private String projectInfo;
    private Duration timeInProject;
    private Long userId;
}
