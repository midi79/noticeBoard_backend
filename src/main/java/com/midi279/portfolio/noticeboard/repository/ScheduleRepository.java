package com.midi279.portfolio.noticeboard.repository;

import com.midi279.portfolio.noticeboard.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByDateOrderByFromHour(LocalDate date);
    List<Schedule> findAllByDateBetween(LocalDate fromDate, LocalDate toDate);
}
