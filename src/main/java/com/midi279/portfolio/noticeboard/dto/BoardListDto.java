package com.midi279.portfolio.noticeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {
    private Long id;
    private String title;
    private Boolean favorite;
    private String writer;
    private Integer count;
    private LocalDateTime editDate;

    @Override
    public String toString() {
        return "BoardDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", favorite=" + favorite +
                ", writer='" + writer + '\'' +
                ", count=" + count +
                ", editDate=" + editDate +
                '}';
    }
}
