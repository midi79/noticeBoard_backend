package com.midi279.portfolio.noticeboard.controller;


import com.midi279.portfolio.noticeboard.dto.ScheduleDto;
import com.midi279.portfolio.noticeboard.dto.ScheduleListDto;
import com.midi279.portfolio.noticeboard.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Operation(summary = "Get Schedule by ID", description = "Get Schedule by specific ID")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @Operation(summary = "Create new schedule", description = "Create new schedule with data")
    @PostMapping("/create")
    public ResponseEntity<ScheduleDto> CreateSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDto));
    }

    @Operation(summary = "Get all schedule by date", description = "Get all schedule by specific one day")
    @GetMapping("/day")
    public ResponseEntity<List<ScheduleDto>> getScheduleByDate(@RequestParam String date) {
        return ResponseEntity.ok(scheduleService.getScheduleByDate(date));
    }

    @Operation(summary = "Get all schedule between two date", description = "Get all schedule between from-date to to-date")
    @GetMapping("/month")
    public ResponseEntity<List<ScheduleListDto>> getScheduleBetweenDate(@RequestParam String fromDate, @RequestParam String toDate) {
        return ResponseEntity.ok(scheduleService.getScheduleBetweenDate(fromDate, toDate));
    }

    @Operation(summary = "Update schedule", description = "Update schedule by ID")
    @PatchMapping("/update")
    public ResponseEntity<ScheduleDto> updateSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleDto));
    }

    @Operation(summary = "Delete schedule", description = "Delete schedule by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
