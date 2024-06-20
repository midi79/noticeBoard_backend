package com.midi279.portfolio.noticeboard.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("Schedule not found with id : " + id);
    }
}
