package org.krainet.timetracker.Service;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.dto.ProjectDTO;
import org.krainet.timetracker.dto.RecordDTO;

import org.krainet.timetracker.dto.TaskDTO;
import org.krainet.timetracker.model.Record;
import org.krainet.timetracker.model.Task;
import org.krainet.timetracker.model.User;
import org.krainet.timetracker.repository.ProjectRepository;
import org.krainet.timetracker.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public Optional<Record> findById(Long id) {
        return recordRepository.findById(id);
    }



    public Record create(Record record)
    {

        return recordRepository.save(record);
    }


    public Record setStartTime(Long recordId) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));
        record.setStartTime(LocalDateTime.now());
        return recordRepository.save(record);
    }


    public List<Record> readAll() {

        return recordRepository.findAll();
    }

    public Optional<Record> getCurrentRecord(User user, Task task) {
        return recordRepository.findActiveRecordByUserAndTask(user, task);
    }

    public Record update(Record project) {

        return recordRepository.save(project);
    }

    public void delete(Long id) {

        recordRepository.deleteById(id);
    }

    public static void trackTime() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final LocalDateTime startTime = LocalDateTime.now();

        Runnable task = new Runnable() {
            long secondsElapsed = 0;

            @Override
            public void run() {
                secondsElapsed++;

                // Вычисление пройденного времени
                long days = secondsElapsed / (24 * 3600);
                long hours = (secondsElapsed % (24 * 3600)) / 3600;
                long minutes = (secondsElapsed % 3600) / 60;
                long seconds = secondsElapsed % 60;

                // Получение текущего времени, основанного на старте таймера
                LocalDateTime currentDateTime = startTime.plus(secondsElapsed, ChronoUnit.SECONDS);

                // Вывод результатов
                System.out.printf("Days: %d, Hours: %d, Minutes: %d, Seconds: %d%n", days, hours, minutes, seconds);
                System.out.println("Current DateTime: " + currentDateTime);
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
}