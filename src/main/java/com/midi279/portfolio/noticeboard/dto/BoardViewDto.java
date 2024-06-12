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
public class BoardViewDto {
    private Long id;
    private String title;
    private String bodyContent;
    private Boolean favorite;
    private String writer;
    private Integer count;
    private Set<String> tags;
    private LocalDateTime editDate;

    @Override
    public String toString() {
        return "BoardViewDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bodyContent='" + bodyContent + '\'' +
                ", favorite=" + favorite +
                ", writer='" + writer + '\'' +
                ", count=" + count +
                ", tags=" + tags +
                ", editDate=" + editDate +
                '}';
    }
}
