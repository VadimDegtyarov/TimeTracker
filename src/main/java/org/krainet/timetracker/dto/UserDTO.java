package org.krainet.timetracker.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private String password;
}
