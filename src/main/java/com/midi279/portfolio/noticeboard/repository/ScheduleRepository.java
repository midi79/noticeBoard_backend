package com.midi279.portfolio.noticeboard.repository;

import com.midi279.portfolio.noticeboard.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByDateOrderByFromHour(LocalDate date);
    List<Schedule> findAllByDateBetween(LocalDate fromDate, LocalDate toDate);
}
