package com.midi279.portfolio.noticeboard.schedule;

import com.midi279.portfolio.noticeboard.dto.ScheduleDto;
import com.midi279.portfolio.noticeboard.dto.ScheduleListDto;
import com.midi279.portfolio.noticeboard.model.Schedule;
import com.midi279.portfolio.noticeboard.repository.ScheduleRepository;
import com.midi279.portfolio.noticeboard.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ScheduleTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private ScheduleDto scheduleDto1;
    private ScheduleDto scheduleDto2;
    private ScheduleDto scheduleDto3;
    private ScheduleDto scheduleDto4;

    @BeforeEach
    void setUp() {
        scheduleDto1 = new ScheduleDto();
        scheduleDto1.setDate("24/06/9999"); // prevent side-effect
        scheduleDto1.setTitle("Test 01");
        scheduleDto1.setDescription("Description 01");
        scheduleDto1.setFromHour("00");
        scheduleDto1.setToHour("00");
        scheduleDto1.setColor("red");

        scheduleDto2 = new ScheduleDto();
        scheduleDto2.setDate("24/06/9999");
        scheduleDto2.setTitle("Test 02");
        scheduleDto2.setDescription("Description 02");
        scheduleDto2.setFromHour("01");
        scheduleDto2.setToHour("02");
        scheduleDto2.setColor("indigo");

        scheduleDto3 = new ScheduleDto();
        scheduleDto3.setDate("22/06/9999");
        scheduleDto3.setTitle("Test 03");
        scheduleDto3.setDescription("Description 03");
        scheduleDto3.setFromHour("02");
        scheduleDto3.setToHour("03");
        scheduleDto3.setColor("gray");

        scheduleDto4 = new ScheduleDto();
        scheduleDto4.setDate("25/06/9999");
        scheduleDto4.setTitle("Test 04");
        scheduleDto4.setDescription("Description 04");
        scheduleDto4.setFromHour("03");
        scheduleDto4.setToHour("04");
        scheduleDto4.setColor("green");
    }


    @Test
    public void createSchedule() {
        // given
        // when
        ScheduleDto savedScheduleDto = scheduleService.createSchedule(scheduleDto1);
        Optional<Schedule> schedule = scheduleRepository.findById(savedScheduleDto.getId());

        // then
        assertThat(schedule.get().getTitle()).isEqualTo("Test 01");
        assertThat(schedule.get().getDescription()).isEqualTo("Description 01");
    }

    @Test
    public void getScheduleById() {
        // given
        ScheduleDto scheduleDto = scheduleService.createSchedule(scheduleDto1);

        // when
        ScheduleDto savedScheduleDto = scheduleService.getScheduleById(scheduleDto.getId());

        // then
        assertThat(savedScheduleDto.getTitle()).isEqualTo("Test 01");
        assertThat(savedScheduleDto.getDescription()).isEqualTo("Description 01");
        assertThat(savedScheduleDto.getDate()).isEqualTo("24/06/9999");
        assertThat(savedScheduleDto.getFromHour()).isEqualTo("00");
        assertThat(savedScheduleDto.getToHour()).isEqualTo("00");
        assertThat(savedScheduleDto.getColor()).isEqualTo("red");
    }

    @Test
    public void getScheduleByDate() {
        // given
        ScheduleDto schedule1 = scheduleService.createSchedule(scheduleDto1);
        ScheduleDto schedule2 = scheduleService.createSchedule(scheduleDto2);

        // when
        List<ScheduleDto> scheduleByDate = scheduleService.getScheduleByDate("24/06/9999");

        // then
        assertThat(scheduleByDate.isEmpty()).isFalse();
        assertThat(scheduleByDate.size()).isEqualTo(2);
        assertThat(scheduleByDate.get(0).getDate()).isEqualTo("24/06/9999");
        assertThat(scheduleByDate.get(1).getDate()).isEqualTo("24/06/9999");
    }


    @Test
    public void getScheduleBetweenDate() {
        // given
        ScheduleDto schedule1 = scheduleService.createSchedule(scheduleDto1);
        ScheduleDto schedule3 = scheduleService.createSchedule(scheduleDto3);

        // when
        List<ScheduleListDto> scheduleBetweenDate = scheduleService.getScheduleBetweenDate("22/06/9999", "24/06/9999");

        scheduleBetweenDate.forEach(System.out::println);
        // then
        assertThat(scheduleBetweenDate.size()).isEqualTo(2);
    }


    @Test
    public void updateSchedule() {
        // given
        ScheduleDto schedule = scheduleService.createSchedule(scheduleDto1);

        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setTitle("Updated Title");
        scheduleDto.setDescription("Updated Description");
        scheduleDto.setFromHour("22");
        scheduleDto.setToHour("23");
        scheduleDto.setColor("coral");

        // when
        ScheduleDto updatedScheduleDto = scheduleService.updateSchedule(scheduleDto);
        Optional<Schedule> updateSchedule = scheduleRepository.findById(updatedScheduleDto.getId());

        // then
        LocalDate date = LocalDate.of(9999, 6, 24);
        assertThat(updateSchedule.get().getDate()).isEqualTo(date);
        assertThat(updateSchedule.get().getTitle()).isEqualTo("Updated Title");
        assertThat(updateSchedule.get().getDescription()).isEqualTo("Updated Description");
        assertThat(updateSchedule.get().getFromHour()).isEqualTo("22");
        assertThat(updateSchedule.get().getToHour()).isEqualTo("23");
        assertThat(updateSchedule.get().getColor()).isEqualTo("coral");
    }

    @Test
    public void deleteSchedule() {
        // given
        ScheduleDto scheduleDto = scheduleService.createSchedule(scheduleDto1);

        // when
        Optional<Schedule> scheduleBefore = scheduleRepository.findById(scheduleDto.getId());

        // then
        assertThat(scheduleBefore).isPresent();

        // when
        scheduleService.deleteSchedule(scheduleDto.getId());
        Optional<Schedule> scheduleAfter = scheduleRepository.findById(scheduleDto.getId());

        // then
        assertThat(scheduleAfter).isNotPresent();
    }



}
