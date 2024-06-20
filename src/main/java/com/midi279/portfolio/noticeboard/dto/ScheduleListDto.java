package com.midi279.portfolio.noticeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleListDto {
    private Long id;
    private String date;
    private String title;
    private String color;

    @Override
    public String toString() {
        return "ScheduleListDto{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
