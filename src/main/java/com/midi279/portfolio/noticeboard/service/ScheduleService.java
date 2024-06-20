package com.midi279.portfolio.noticeboard.service;

import com.midi279.portfolio.noticeboard.dto.ScheduleDto;
import com.midi279.portfolio.noticeboard.dto.ScheduleListDto;
import com.midi279.portfolio.noticeboard.model.Schedule;
import com.midi279.portfolio.noticeboard.repository.ScheduleRepository;
import com.midi279.portfolio.noticeboard.util.ScheduleMapper;
import com.midi279.portfolio.noticeboard.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    /**
     * Get Schedule by ID
     * @param id Long
     * @return ScheduleDto
     */
    public ScheduleDto getScheduleById(Long id) {
        return ScheduleMapper.toDto(scheduleRepository.findById(id).orElseThrow());
    }

    /**
     * Create schedule
     * @param scheduleDto
     * @return ScheduleDto
     */
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = ScheduleMapper.toEntity(scheduleDto);
        return ScheduleMapper.toDto(scheduleRepository.save(schedule));
    }


    /**
     * Get Schedule list by specific date
     * @param date String
     * @return List<ScheduleDto>
     */
    public List<ScheduleDto> getScheduleByDate(String date) {
        return scheduleRepository.findAllByDateOrderByFromHour(DateUtil.convertStringToLocalDate(date))
                .stream().map(ScheduleMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get Schedule list by between from-date to to-date
     * @param fromDate String
     * @param toDate String
     * @return List<ScheduleDto>
     */
    public List<ScheduleListDto> getScheduleBetweenDate(String fromDate, String toDate) {
        return scheduleRepository.findAllByDateBetween(DateUtil.convertStringToLocalDate(fromDate), DateUtil.convertStringToLocalDate(toDate))
                .stream().map(ScheduleMapper::toListDto).collect(Collectors.toList());
    }


    /**
     * Update schedule
     * @param scheduleDto
     * @return ScheduleDto
     */
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.findById(scheduleDto.getId()).orElseThrow();
        schedule.setTitle(scheduleDto.getTitle());
        schedule.setDescription(scheduleDto.getDescription());
        schedule.setFromHour(scheduleDto.getFromHour());
        schedule.setToHour(scheduleDto.getToHour());
        schedule.setColor(scheduleDto.getColor());
        return ScheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    /**
     * Delete Schedule by ID
     * @param id
     */
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }


}
