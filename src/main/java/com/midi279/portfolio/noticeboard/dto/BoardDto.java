package com.midi279.portfolio.noticeboard.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto extends BoardViewDto {
    private String password;

    public BoardDto(Long id, String title, String bodyContent, Boolean favorite, String writer, String password, Integer count, Set<String> tags, LocalDateTime editDate) {
        super(id, title, bodyContent, favorite, writer, count, tags, editDate);
        this.password = password;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "password='" + password + '\'' +
                "} " + super.toString();
    }
}
