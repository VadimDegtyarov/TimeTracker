package org.krainet.timetracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "records")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record {
    @Id
    private Long id;


    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "time_record")
    private Long timeRecord;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;


}
