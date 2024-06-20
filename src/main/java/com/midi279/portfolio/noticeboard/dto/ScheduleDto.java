package com.midi279.portfolio.noticeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long id;
    private String date;
    private String title;
    private String description;
    private String fromHour;
    private String toHour;
    private String color;

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", fromHour='" + fromHour + '\'' +
                ", toHour='" + toHour + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
