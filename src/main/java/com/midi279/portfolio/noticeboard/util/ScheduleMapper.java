package com.midi279.portfolio.noticeboard.util;

import com.midi279.portfolio.noticeboard.dto.ScheduleDto;
import com.midi279.portfolio.noticeboard.dto.ScheduleListDto;
import com.midi279.portfolio.noticeboard.model.Schedule;

public class ScheduleMapper {
    public static ScheduleDto toDto(Schedule schedule) {
        return new ScheduleDto(schedule.getId(), DateUtil.convertLocalDateToString(schedule.getDate()), schedule.getTitle(), schedule.getDescription(),
                schedule.getFromHour(), schedule.getToHour(), schedule.getColor());
    }

    public static Schedule toEntity(ScheduleDto scheduleDto) {
        if (scheduleDto == null) {
            return null;
        }

        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setDate(DateUtil.convertStringToLocalDate(scheduleDto.getDate()));
        schedule.setTitle(scheduleDto.getTitle());
        schedule.setDescription(scheduleDto.getDescription());
        schedule.setFromHour(scheduleDto.getFromHour());
        schedule.setToHour(scheduleDto.getToHour());
        schedule.setColor(scheduleDto.getColor());

        return schedule;
    }

    public static ScheduleListDto toListDto(Schedule schedule) {
        return new ScheduleListDto(schedule.getId(), DateUtil.convertLocalDateToString(schedule.getDate()), schedule.getTitle(),
                schedule.getColor());
    }
}
